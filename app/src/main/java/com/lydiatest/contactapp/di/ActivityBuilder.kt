package com.lydiatest.contactapp.di

import com.lydiatest.contactapp.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/* Created by *-----* Alexandre Thauvin *-----* */

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [FragmentBuilder::class])
    internal abstract fun contributeMainActivity(): MainActivity
}