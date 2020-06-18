package com.lydiatest.contactapp.utils

import android.content.Context
import android.content.SharedPreferences
import io.reactivex.Observable

/* Created by *-----* Alexandre Thauvin *-----* */

open class DataStorage(context: Context, prefContext: String = "DefaultAppPreferences") {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(prefContext, Context.MODE_PRIVATE)


    /***
     * Add a string to the shared preferences
     * @param key The key
     * @param value The string to store
     */
    fun putString(key: String, value: String): Observable<Void> {
         return Observable.create { subscriber ->
            prefs.edit()
                .putString(key, value)
                .apply()
            subscriber.onComplete()
        }
    }



    /***
     * Add a boolean to the shared preferences
     * @param key The key
     * @param value The boolean to store
     */
    fun putBoolean(key: String, value: Boolean): Observable<Boolean> {
        return Observable.create { subscriber ->
            prefs.edit()
                .putBoolean(key, value)
                .apply()
            subscriber.onComplete()
        }
    }

    /**
     * Add an int to the shared preferences
     * @param key The key
     * @param value The int to store
     */
    fun putInt(key: String, value: Int): Observable<Void> {
        return Observable.create { subscriber ->
            prefs.edit()
                .putInt(key, value)
                .apply()
            subscriber.onComplete()
        }
    }

    /**
     * Add a float to the shared preferences
     * @param key The key
     * @param value The float to store
     */
    fun putFloat(key: String, value: Float): Observable<Void> {
        return Observable.create { subscriber ->
            prefs.edit()
                .putFloat(key, value)
                .apply()
            subscriber.onComplete()
        }
    }

    /***
     * Remove a string to the shared preferences
     * @param key The key
     *
     */
    fun removeString(key: String): Observable<Void> {
        return Observable.create { subscriber ->
            prefs.edit()
                .remove(key)
                .apply()
            subscriber.onComplete()
        }
    }

    /***
     * Retrieve a string from the shared preferences
     * @param key The key of the value needed
     */
    fun getString(key: String): String {
        val value: String? = prefs.getString(key, "")
        return value ?: ""
    }


    /***
     * Retrieve a boolean from the shared preferences
     * @param key The key of the value needed
     */
    fun getBoolean(key: String): Boolean = prefs.getBoolean(key, false)


    /***
     * Retrieve a int from the shared preferences
     * @param key The key of the value needed
     */
    fun getInt(key: String): Int = prefs.getInt(key, 0)


    /***
     * Retrieve a float from the shared preferences
     * @param key The key of the value needed
     */
    fun getFloat(key: String): Float = prefs.getFloat(key, 0f)
}