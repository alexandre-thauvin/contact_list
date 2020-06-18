package com.lydiatest.contactapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lydiatest.contactapp.model.ContactResult

/* Created by *-----* Alexandre Thauvin *-----* */

@Database(entities = [ContactResult.Contact::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
}