package com.lydiatest.contactapp.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
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

interface ContactAppComponent {
    //fun inject(messagingViewHolder: MessagingListAdapter.MessagingListViewHolder)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ContactAppComponent
    }
}