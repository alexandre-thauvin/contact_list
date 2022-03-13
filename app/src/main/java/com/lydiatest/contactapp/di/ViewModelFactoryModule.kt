package com.lydiatest.contactapp.di

import com.lydiatest.contactapp.api.Repository
import com.lydiatest.contactapp.viewmodels.ContactListViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/* Created by *-----* Alexandre Thauvin *-----* */

@Module
class ViewModelFactoryModule {

    @Singleton
    @Provides
    fun provideContactListViewModelFactory(repository: Repository) = ContactListViewModelFactory(repository)
}