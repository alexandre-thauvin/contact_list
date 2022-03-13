package com.lydiatest.contactapp.base

import android.content.Context
import androidx.fragment.app.Fragment
import dagger.android.support.AndroidSupportInjection

/* Created by *-----* Alexandre Thauvin *-----* */

open class BaseFragment : Fragment() {
    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}