package com.lydiatest.contactapp.api

import com.lydiatest.contactapp.api.exceptions.BadRequestException
import com.lydiatest.contactapp.api.exceptions.ServerErrorException
import okhttp3.Interceptor
import okhttp3.Response
import java.net.HttpURLConnection

/* Created by *-----* Alexandre Thauvin *-----* */

class ApiInterceptor : Interceptor {

    /***
     * Intercept API response and
     * handle depending on the code
     */
    override fun intercept(chain: Interceptor.Chain): Response  {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
        val response = chain.proceed(requestBuilder.build())

        when (response.code()){
            ApiCode.BAD_REQUEST -> throw BadRequestException()
            ApiCode.SERVER_ERROR -> throw ServerErrorException()
            else -> return response
        }
    }
}