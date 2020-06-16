package com.lydiatest.contactapp.api

import com.lydiatest.contactapp.model.ContactResult
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

/* Created by *-----* Alexandre Thauvin *-----* */

@Singleton
class Repository @Inject constructor(private val apiService: ApiService) {

    fun getContactsByPage(pageSize: String, page: String): Observable<ContactResult> = apiService.getContactsByPage(pageSize, page)
}