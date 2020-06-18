package com.lydiatest.contactapp.di

import android.app.Application
import androidx.room.Room
import com.lydiatest.contactapp.data.AppDatabase
import com.lydiatest.contactapp.data.ContactDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/* Created by *-----* Alexandre Thauvin *-----* */

@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, "contacts-db")
            .build()
    }

    @Provides
    @Singleton
    fun provideContactsDao(database: AppDatabase): ContactDao = database.contactDao()

}