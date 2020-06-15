package com.lydiatest.contactapp.di

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.lydiatest.contactapp.api.ApiService
import com.lydiatest.contactapp.utils.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/* Created by *-----* Alexandre Thauvin *-----* */

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideSchedulerProvider() = SchedulerProvider(
        Schedulers.io(),
        AndroidSchedulers.mainThread()
    )

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(application: Application): OkHttpClient {
        val okClientBuilder = OkHttpClient.Builder()
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        val cacheDir = File(application.cacheDir, UUID.randomUUID().toString())
        // 10 MiB cache
        val cache = Cache(cacheDir, 10 * 1024 * 1024)
        okClientBuilder.addInterceptor { chain ->
            val original = chain.request()

            val requestBuilder = original.newBuilder()
                .header("Content-Type", "application/json")

            val request = requestBuilder.build()
            chain.proceed(request)
        }
        okClientBuilder.cache(cache)
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okClientBuilder.addInterceptor(httpLoggingInterceptor)
        okClientBuilder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        okClientBuilder.readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        okClientBuilder.writeTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        return okClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideApiService(gson: Gson, okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build().create(ApiService::class.java)
    }

    companion object {
        private const val CONNECTION_TIMEOUT: Long = 60
        private const val BASE_URL = "https://randomuser.me/api/" //Here because no difference between release and debug
    }
}