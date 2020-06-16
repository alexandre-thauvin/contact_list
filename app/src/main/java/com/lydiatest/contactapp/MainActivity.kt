package com.lydiatest.contactapp

import android.os.Bundle
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.lydiatest.contactapp.base.BaseActivity
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_toolbar.view.*
import javax.inject.Inject

class MainActivity : BaseActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setFragment(ContactListFragment::class.java, null)
    }

    private fun setFragment(
        fragmentClass: Class<out Fragment>,
        @Nullable bundle: Bundle?
    ) {
        toolbar.toolbar_title.text = when (fragmentClass.name){
            ContactListFragment::class.java.name -> getString(R.string.main_activity_toolbar_title)
            else ->  getString(R.string.main_activity_toolbar_title)
        }
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.rlMain, fragmentClass, bundle, fragmentClass.name)
            .commit()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }
}