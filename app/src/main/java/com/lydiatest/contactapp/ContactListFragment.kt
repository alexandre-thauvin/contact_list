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
import com.lydiatest.contactapp.viewmodels.ContactListViewModel
import kotlinx.android.synthetic.main.fragment_contact_list.*
import timber.log.Timber
import java.net.UnknownHostException
import javax.inject.Inject

/* Created by *-----* Alexandre Thauvin *-----* */

class ContactListFragment: BaseFragment() {

    private lateinit var adapter: ContactListAdapter

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
        initView()
        initListeners()
    }

    private fun initView(){
        val layoutManager = LinearLayoutManager(activity)
        rvContact.layoutManager = layoutManager
        rvContact.itemAnimator = DefaultItemAnimator()
        adapter = ContactListAdapter(this::onContactClicked)
        rvContact.adapter = adapter
        getContacts()
    }

    private fun initListeners(){
        srList.setOnRefreshListener { viewModel.page = 1; viewModel.contacts.clear(); getContacts() }

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

    private fun getContacts(){
        srList.isRefreshing = true
        disposable.add(viewModel.getContactByPages()
            .doOnError {e->
                srList.isRefreshing = false
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
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
            }
            .subscribe({result ->
                srList.isRefreshing = false
                viewModel.page++
                viewModel.contacts.addAll(result.contacts)
                adapter.updateList(viewModel.contacts)
                tvNumberOfResult.text = getString(R.string.contact_list_fragment_number_of_result, viewModel.contacts.size)
            }, Throwable::printStackTrace)
        )
    }

    private fun onContactClicked(contact: ContactResult.Contact){

    }
}