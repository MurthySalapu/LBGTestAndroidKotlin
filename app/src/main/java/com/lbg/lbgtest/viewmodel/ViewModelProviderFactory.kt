package com.lbg.lbgtest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ccom.lbg.lbgtest.viewmodel.TestFMFragmentViewModel

/**
 * View Model Provider Factory
 * @param <V>
 */


@Suppress("UNCHECKED_CAST")
class ViewModelProviderFactory<T>(private val viewModel: T) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(TestFMActivityViewModel::class.java)){
            return viewModel as T
        }else if(modelClass.isAssignableFrom(TestFMFragmentViewModel::class.java)){
            return  viewModel as T
        }

        throw NullPointerException("Unknown class name")
    }
}
