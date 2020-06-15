package com.lydiatest.contactapp.base

import android.content.Context
import androidx.fragment.app.Fragment
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable

/* Created by *-----* Alexandre Thauvin *-----* */

open class BaseFragment : Fragment() {

    protected var disposable = CompositeDisposable()

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}