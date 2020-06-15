package com.lydiatest.contactapp.api

import com.lydiatest.contactapp.model.ContactResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/* Created by *-----* Alexandre Thauvin *-----* */

interface ApiService {
    @GET("1.0/?seed=lydia&results={nbResult}&page={page}")
    fun getContactsByPage(@Path("nbResult") nbResult: String, @Path("page") page: String): Observable<ContactResult>
}