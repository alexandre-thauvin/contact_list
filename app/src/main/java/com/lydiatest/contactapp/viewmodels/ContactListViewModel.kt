package com.lydiatest.contactapp.viewmodels

import com.lydiatest.contactapp.api.Repository
import com.lydiatest.contactapp.data.ContactDao
import com.lydiatest.contactapp.model.ContactResult
import com.lydiatest.contactapp.utils.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Observable

/* Created by *-----* Alexandre Thauvin *-----* */

class ContactListViewModel(private val repository: Repository, private val schedulerProvider: SchedulerProvider, private val contactDao: ContactDao) {
    var page = 1
    var contacts = mutableListOf<ContactResult.Contact>()
    var launch = true
    fun getContactByPages(): Observable<ContactResult> {
        return repository.getContactsByPage(PAGE_SIZE.toString(), page.toString())
            .compose(schedulerProvider.getSchedulersForObservable())
    }

    fun getContactFromDatabase(): Observable<List<ContactResult.Contact>>{
        return contactDao.getContacts()
            .compose(schedulerProvider.getSchedulersForObservable())
    }

    fun insertAllContactsToDataBase(): Completable {
        return contactDao.insertAllContatcs(contacts)
            .compose(schedulerProvider.getSchedulersForCompletable())
    }

    fun cleanContactList(): Completable{
        return contactDao.cleanContactList()
            .compose(schedulerProvider.getSchedulersForCompletable())
    }

    companion object {
        const val PAGE_SIZE = 10
    }
}