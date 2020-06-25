package com.lydiatest.contactapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lydiatest.contactapp.adapters.ContactListAdapter
import com.lydiatest.contactapp.api.exceptions.BadRequestException
import com.lydiatest.contactapp.api.exceptions.ServerErrorException
import com.lydiatest.contactapp.base.BaseFragment
import com.lydiatest.contactapp.model.ContactResult
import com.lydiatest.contactapp.utils.DataStorage
import com.lydiatest.contactapp.viewmodels.ContactListViewModel
import kotlinx.android.synthetic.main.fragment_contact_list.*
import timber.log.Timber
import java.net.UnknownHostException
import javax.inject.Inject

/* Created by *-----* Alexandre Thauvin *-----* */

class ContactListFragment : BaseFragment() {

    private lateinit var adapter: ContactListAdapter

    private val contactDetailDialog = ContactDetailDialog()

    private lateinit var dataStorage: DataStorage

    @Inject
    lateinit var viewModel: ContactListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contact_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataStorage = DataStorage(requireContext())
        initView()
        initListeners()
    }

    private fun initView() {
        val layoutManager = LinearLayoutManager(activity)
        rvContact.layoutManager = layoutManager
        rvContact.itemAnimator = DefaultItemAnimator()
        adapter = ContactListAdapter(this::onContactClicked)
        rvContact.adapter = adapter
        getContacts()
    }

    private fun initListeners() {
        srList.setOnRefreshListener {
            viewModel.page = 1; viewModel.contacts.clear(); getContacts()
        }

        rvContact.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val manager = rvContact.layoutManager as LinearLayoutManager
                val visibleItemCount: Int = manager.childCount
                val totalItemCount: Int = manager.itemCount
                val firstVisibleItemPosition: Int = manager.findFirstVisibleItemPosition()
                if (!srList.isRefreshing) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= ContactListViewModel.PAGE_SIZE
                    ) {
                        getContacts()
                    }
                }
            }
        })
    }

    private fun getContacts() {
        srList.isRefreshing = true
        disposable.add(viewModel.getContactByPages()
            .doOnError { e ->
                srList.isRefreshing = false
                if (viewModel.launch) {
                    Timber.e("get contacts database")
                    getContactsFromDatabase()
                }
                e.localizedMessage?.let {
                    Timber.e(it)
                }
                val message = when (e) {
                    is BadRequestException -> {
                        getString(R.string.common_error_bad_request)
                    }
                    is ServerErrorException -> {
                        getString(R.string.common_error_server_error)
                    }
                    is UnknownHostException -> {
                        getString(R.string.common_error_no_connection)
                    }
                    else -> {
                        getString(R.string.common_error_basic)
                    }
                }
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
            .subscribe({ result ->
                srList.isRefreshing = false
                viewModel.page++
                if (dataStorage.getBoolean(IS_FROM_CACHE)) {//need a variable to clean the database after a first successful fetch
                    dataStorage.putBoolean(IS_FROM_CACHE, false).subscribe()
                    viewModel.contacts.clear()
                    cleanContactListOfDatabase()
                }
                viewModel.contacts.addAll(result.contacts)
                saveContactsToDatabase()
                adapter.updateList(viewModel.contacts)
                tvNumberOfResult.text = getString(
                    R.string.contact_list_fragment_number_of_result,
                    viewModel.contacts.size
                )
            }, Throwable::printStackTrace)
        )
    }

    private fun getContactsFromDatabase() {
        srList.isRefreshing = true
        disposable.add(viewModel.getContactFromDatabase()
            .doOnError {
                srList.isRefreshing = false
            }
            .subscribe({
                srList.isRefreshing = false
                viewModel.launch = false
                viewModel.contacts.addAll(it)
                adapter.updateList(viewModel.contacts)
                tvNumberOfResult.text = getString(
                    R.string.contact_list_fragment_number_of_result,
                    viewModel.contacts.size
                )
                dataStorage.putBoolean(IS_FROM_CACHE, true).subscribe()
            }, Throwable::printStackTrace)
        )
    }

    private fun saveContactsToDatabase() {
        disposable.add(viewModel.insertAllContactsToDataBase()
            .doOnError {
                Timber.e("Insert error")
            }
            .subscribe({
                Timber.d("Contacts saved")
            }, Throwable::printStackTrace)
        )
    }

    private fun cleanContactListOfDatabase() {
        disposable.add(viewModel.cleanContactList()
            .doOnError {
                Timber.e("clean table error")

            }
            .subscribe({
                Timber.e("Table cleaned")
            }, Throwable::printStackTrace)
        )
    }

    private fun onContactClicked(contact: ContactResult.Contact) {
        val bundle = Bundle()
        bundle.putSerializable(ContactDetailDialog.CONTACT, contact)
        contactDetailDialog.arguments = bundle
        contactDetailDialog.show(parentFragmentManager, ContactDetailDialog::class.java.name)
    }

    companion object {
        const val IS_FROM_CACHE = "IS_FROM_CACHE"
    }
}