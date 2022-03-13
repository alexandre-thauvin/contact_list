package com.lydiatest.contactapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lydiatest.contactapp.R
import com.lydiatest.contactapp.api.Repository
import com.lydiatest.contactapp.api.exceptions.BadRequestException
import com.lydiatest.contactapp.api.exceptions.ServerErrorException
import com.lydiatest.contactapp.model.ContactResult
import com.lydiatest.contactapp.model.LoadingState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.UnknownHostException

/* Created by *-----* Alexandre Thauvin *-----* */

class ContactListViewModel(private val repository: Repository) :
    ViewModel() {
    var page = 1
    val contactsLiveData: MutableLiveData<ContactResult> = MutableLiveData()
    val loadingState: MutableLiveData<LoadingState> = MutableLiveData()
    var isFirstNetworkCall = true
    var isInit = true

    init {
        viewModelScope.launch {
            repository.contacts
                .catch { Timber.e("collect failed") }
                .collect { list ->
                    if (!isInit) {
                        contactsLiveData.postValue(ContactResult(list))
                    }
                    isInit = false
                }
        }
    }

    fun getContactByPages(forceClean: Boolean) {
        if (loadingState.value?.state != LoadingState.Status.RUNNING) {
            viewModelScope.launch {
                loadingState.postValue(LoadingState(LoadingState.Status.RUNNING))
                try {
                    val contacts = repository.getContactsByPage(pageSize = PAGE_SIZE, page)
                    Timber.e("get from network OK")
                    if (isFirstNetworkCall || forceClean) {
                        repository.cleanContactList()
                    }
                    repository.insertAllContactsToDataBase(contacts.contacts)
                    Timber.e("insert OK")
                    loadingState.postValue(LoadingState(LoadingState.Status.SUCCESS))
                    isFirstNetworkCall = false
                    page++
                } catch (e: Exception) {
                    if (isFirstNetworkCall) {
                        Timber.e("get contacts database")
                        getContactsFromDatabase()
                    }
                    loadingState.postValue(
                        LoadingState(
                            LoadingState.Status.FAILED,
                            resId = getErrorResId(e)
                        )
                    )
                }
            }
        }
    }

    private fun getContactsFromDatabase() {
        viewModelScope.launch {
            try {
                loadingState.postValue(LoadingState(LoadingState.Status.RUNNING))
                contactsLiveData.postValue(ContactResult(repository.getContactsFromDatabase()))
                loadingState.postValue(LoadingState(LoadingState.Status.SUCCESS))
            } catch (e: Exception) {
                loadingState.postValue(LoadingState(LoadingState.Status.FAILED, resId = getErrorResId(e)))
            }

        }
    }

    private fun getErrorResId(e: Exception) =  when (e) {
            is BadRequestException -> {
                R.string.common_error_bad_request
            }
            is ServerErrorException -> {
                R.string.common_error_server_error
            }
            is UnknownHostException -> {
                R.string.common_error_no_connection
            }
            else -> {
                R.string.common_error_basic
            }
        }

    companion object {
        const val PAGE_SIZE = 10
    }
}