package com.lydiatest.contactapp.di

import android.app.Application
import com.lydiatest.contactapp.adapters.ContactListAdapter
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/* Created by *-----* Alexandre Thauvin *-----* */

@Singleton
@Component(
    modules =
    [AndroidSupportInjectionModule::class,
        NetworkModule::class,
        ManagerModule::class,
        ActivityBuilder::class]
)

interface ContactAppComponent : AndroidInjector<ContactApp> {
    fun inject(contactViewHolder: ContactListAdapter.ContactViewHolder)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ContactAppComponent
    }
}