package com.lydiatest.contactapp.di

import com.lydiatest.contactapp.api.Repository
import com.lydiatest.contactapp.data.ContactDao
import com.lydiatest.contactapp.utils.SchedulerProvider
import com.lydiatest.contactapp.viewmodels.ContactListViewModel
import dagger.Module
import dagger.Provides

/* Created by *-----* Alexandre Thauvin *-----* */

@Module
class ManagerModule {

    @Provides
    fun provideContactListViewModel(repository: Repository, contactDao: ContactDao) = ContactListViewModel(repository)
}