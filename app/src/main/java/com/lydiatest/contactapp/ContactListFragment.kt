package com.lydiatest.contactapp

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lydiatest.contactapp.adapters.ContactListAdapter
import com.lydiatest.contactapp.base.BaseFragment
import com.lydiatest.contactapp.model.ContactResult
import com.lydiatest.contactapp.model.LoadingState
import com.lydiatest.contactapp.utils.DataStorage
import com.lydiatest.contactapp.viewmodels.ContactListViewModel
import kotlinx.android.synthetic.main.fragment_contact_list.*
import javax.inject.Inject

/* Created by *-----* Alexandre Thauvin *-----* */

class ContactListFragment : BaseFragment() {

    private lateinit var adapter: ContactListAdapter

    private lateinit var contactDetailDialog:ContactDetailDialog

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
        initObservers()
        viewModel.getContactByPages(false)
    }

    private fun initObservers(){
        viewModel.contactsLiveData.observe(viewLifecycleOwner) {
            adapter.updateList(it.contacts)
            tvNumberOfResult.text = getString(
                R.string.contact_list_fragment_number_of_result,
                it.contacts.size
            )
        }
        viewModel.loadingState.observe(viewLifecycleOwner) {
            when (it.state){
                LoadingState.Status.SUCCESS -> {
                    srList.isRefreshing = false
                }
                LoadingState.Status.FAILED -> {
                    srList.isRefreshing = false
                    val msg = if (!TextUtils.isEmpty(it.msg)){
                        it.msg
                    }
                    else {
                        if (it.resId != null)
                            getString(it.resId!!)
                        else
                            getString(R.string.common_error_basic)
                    }
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
                }
                LoadingState.Status.RUNNING -> {
                    srList.isRefreshing = true
                }
            }
        }
    }

    private fun initListeners() {
        srList.setOnRefreshListener {
            viewModel.page = 1
            viewModel.getContactByPages(true)
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
                        viewModel.getContactByPages(false)
                    }
                }
            }
        })
    }

    private fun onContactClicked(contact: ContactResult.Contact) {
        val bundle = Bundle()
        bundle.putSerializable(ContactDetailDialog.CONTACT, contact)
        if (!::contactDetailDialog.isInitialized) {
            contactDetailDialog = ContactDetailDialog()
        }
        contactDetailDialog.arguments = bundle
        contactDetailDialog.show(parentFragmentManager, ContactDetailDialog::class.java.name)
    }
}