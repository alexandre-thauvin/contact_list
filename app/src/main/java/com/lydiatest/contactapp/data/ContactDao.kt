package com.lydiatest.contactapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lydiatest.contactapp.model.ContactResult
import io.reactivex.Completable
import io.reactivex.Single

/* Created by *-----* Alexandre Thauvin *-----* */

@Dao
interface ContactDao {
    @Query("SELECT * FROM contacts")
    fun getContacts(): Single<List<ContactResult.Contact>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllContatcs(contacts: List<ContactResult.Contact>): Completable

    @Query("DELETE FROM contacts")
    fun cleanContactList(): Completable
}