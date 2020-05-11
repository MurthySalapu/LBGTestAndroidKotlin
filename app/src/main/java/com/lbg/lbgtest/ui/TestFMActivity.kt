package com.lbg.lbgtest.ui


import android.os.Bundle

import androidx.lifecycle.ViewModelProviders
import com.lbg.lbgtest.BR

import com.lbg.lbgtest.R

import com.lbg.lbgtest.databinding.ActivityMainBinding
import com.lbg.lbgtest.ui.fragments.TestFMFragment
import com.lbg.lbgtest.viewmodel.TestFMActivityViewModel
import com.lbg.lbgtest.viewmodel.ViewModelProviderFactory


/**
 * Name - Test FM Activity
 * Purpose - Home Page to attach the fragments
 */
class TestFMActivity : BaseActivity<ActivityMainBinding, TestFMActivityViewModel>() {


    private var mViewModel: TestFMActivityViewModel? = null

    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.activity_main

    override val viewModel: TestFMActivityViewModel
        get() {
            val viewModelProviderFactory = ViewModelProviderFactory(TestFMActivityViewModel())
            mViewModel = ViewModelProviders.of(this, viewModelProviderFactory)
                .get(TestFMActivityViewModel::class.java)
            return mViewModel as TestFMActivityViewModel
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchFragment()
    }

    /**
     * Launch Fragment
     */
    private fun launchFragment() {
        val manager = supportFragmentManager
        val fragmentTransaction = manager.beginTransaction()
        fragmentTransaction.replace(R.id.container, TestFMFragment())
        fragmentTransaction.commit()
    }
}
