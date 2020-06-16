package com.lydiatest.contactapp.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection

/* Created by *-----* Alexandre Thauvin *-----* */

open class BaseActivity : AppCompatActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }
}