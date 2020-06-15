package com.lydiatest.contactapp.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable


/* Created by *-----* Alexandre Thauvin *-----* */

open class BaseActivity : AppCompatActivity() {
    protected var disposable = CompositeDisposable()

    public override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    public override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}