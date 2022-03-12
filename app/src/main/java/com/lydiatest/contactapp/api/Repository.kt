package com.lydiatest.contactapp.api

import androidx.annotation.WorkerThread
import com.lydiatest.contactapp.data.ContactDao
import com.lydiatest.contactapp.model.ContactResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/* Created by *-----* Alexandre Thauvin *-----* */

@Singleton
class Repository @Inject constructor(
    private val apiService: ApiService,
    private val contactDao: ContactDao
) {

    val contacts: Flow<List<ContactResult.Contact>> = contactDao.getContactsFlow()

    suspend fun getContactsByPage(pageSize: Int, page: Int): ContactResult =
        apiService.getContactsByPage(pageSize, page)

    @WorkerThread
    suspend fun insertAllContactsToDataBase(contacts: List<ContactResult.Contact>) {
        contactDao.insertAllContacts(contacts)
    }

    suspend fun cleanContactList(): Int {
        return contactDao.cleanContactList()
    }

    suspend fun getContactsFromDatabase(): List<ContactResult.Contact> = contactDao.getContacts()

    companion object {
        val REFRESHING_INTERVAL_MS: Long = 10
    }
}