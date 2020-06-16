package com.lydiatest.contactapp.di

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject

/* Created by *-----* Alexandre Thauvin *-----* */

class ContactApp: Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        contactAppComponent = DaggerContactAppComponent.builder()
            .application(this)
            .build()
        contactAppComponent.inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

    companion object {
        lateinit var contactAppComponent: ContactAppComponent
    }
}