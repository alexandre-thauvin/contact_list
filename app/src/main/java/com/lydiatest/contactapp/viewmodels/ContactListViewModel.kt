package com.lydiatest.contactapp.viewmodels

import com.lydiatest.contactapp.api.Repository
import com.lydiatest.contactapp.model.ContactResult
import com.lydiatest.contactapp.utils.SchedulerProvider
import io.reactivex.Observable

/* Created by *-----* Alexandre Thauvin *-----* */

class ContactListViewModel(private val repository: Repository, private val schedulerProvider: SchedulerProvider) {
    var page = 1
    var contacts = mutableListOf<ContactResult.Contact>()
    fun getContactByPages(): Observable<ContactResult> {
        return repository.getContactsByPage(PAGE_SIZE.toString(), page.toString())
            .compose(schedulerProvider.getSchedulersForObservable())
    }

    companion object {
        const val PAGE_SIZE = 10
    }
}