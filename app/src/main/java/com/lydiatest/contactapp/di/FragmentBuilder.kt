package com.lydiatest.contactapp.di

import com.lydiatest.contactapp.ContactListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/* Created by *-----* Alexandre Thauvin *-----* */

@Module
abstract class FragmentBuilder {

    @ContributesAndroidInjector
    abstract fun contributeContactListFragment(): ContactListFragment
}