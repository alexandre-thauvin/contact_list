package com.lydiatest.contactapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.lydiatest.contactapp.api.ApiInterceptor
import com.lydiatest.contactapp.api.ApiService
import com.lydiatest.contactapp.utils.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
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
    fun provideOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
        return OkHttpClient.Builder()
            .cache(null)
            .addNetworkInterceptor { chain ->
                chain.proceed(
                    chain.request()
                        .newBuilder()
                        .addHeader("Connection", "close")
                        .build()
                )
            }
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(ApiInterceptor())
            .callTimeout(CONNECTION_TIMEOUT, TimeUnit.MINUTES)
            .writeTimeout(CONNECTION_TIMEOUT, TimeUnit.MINUTES)
            .readTimeout(CONNECTION_TIMEOUT, TimeUnit.MINUTES)
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MINUTES)
            .build()
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
        private const val CONNECTION_TIMEOUT: Long = 2
        private const val BASE_URL = "https://randomuser.me/api/" //Here because no difference between release and debug
    }
}