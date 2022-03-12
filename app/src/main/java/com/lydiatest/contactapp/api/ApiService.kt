package com.lydiatest.contactapp.api

import com.lydiatest.contactapp.model.ContactResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/* Created by *-----* Alexandre Thauvin *-----* */

interface ApiService {
    @GET(ApiRouting.URL_GET_CONTACTS)
    suspend fun getContactsByPage(@Query("results") pageSize: Int, @Query("page") page: Int): ContactResult
}