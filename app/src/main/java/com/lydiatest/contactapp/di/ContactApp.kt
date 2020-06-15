package com.lydiatest.contactapp.di

import android.app.Activity
import android.app.Application
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/* Created by *-----* Alexandre Thauvin *-----* */

class ContactApp: Application(), HasActivityInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        contactAppComponent = DaggerContactAppComponent.builder()
            .application(this)
            .build()
        //contactAppComponent.inject(this)
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity> =
        activityDispatchingAndroidInjector

    companion object {
        lateinit var contactAppComponent: ContactAppComponent
    }
}