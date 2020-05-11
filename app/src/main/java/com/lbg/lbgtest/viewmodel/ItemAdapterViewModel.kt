package com.lbg.lbgtest.viewmodel

import androidx.lifecycle.MutableLiveData

import com.lbg.lbgtest.model.CommonResult

/**
 * Name - Item Adapter view model
 * Purpose - Bind the data to View
 */
class ItemAdapterViewModel : BaseViewModel() {

    val url = MutableLiveData<String>()


    fun onCardItemClicked(url: String) {
        this.url.value = url
    }

    fun getData(dataModel: CommonResult): String? {
        when (dataModel.type) {
            0 -> return "Artist:" + dataModel.artist
            1, 2 -> return "Listeners:" + dataModel.listeners
        }
        return null
    }
}
