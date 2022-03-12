package com.lydiatest.contactapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lydiatest.contactapp.model.ContactResult
import io.reactivex.Completable
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

/* Created by *-----* Alexandre Thauvin *-----* */

@Dao
interface ContactDao {
    @Query("SELECT * FROM contacts")
    fun getContactsFlow(): Flow<List<ContactResult.Contact>>

    @Query("SELECT * FROM contacts")
    suspend fun getContacts(): List<ContactResult.Contact>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllContacts(contacts: List<ContactResult.Contact>)

    @Query("DELETE FROM contacts")
    suspend fun cleanContactList(): Int
}