package ccom.lbg.lbgtest.viewmodel


import android.view.View
import android.widget.AdapterView

import androidx.lifecycle.MutableLiveData
import com.lbg.lbgtest.business.SearchBusiness
import com.lbg.lbgtest.common.AppSettings
import com.lbg.lbgtest.enums.SearchType

import com.lbg.lbgtest.model.CommonResult
import com.lbg.lbgtest.viewmodel.BaseViewModel

/**
 * Name - Test FM Fragment view model
 * Purpose - bind the search result to view
 */

class TestFMFragmentViewModel : BaseViewModel() {

    private var mSearchBusiness: SearchBusiness? = null

    val mCommonResult = MutableLiveData<List<CommonResult>>()
    val mError = MutableLiveData<String>()
    var mQuery = ""
    var mSelectedPos = 0

    var loadView = MutableLiveData<Boolean>()

    fun setUp(searchBusiness: SearchBusiness) {
        this.mSearchBusiness = searchBusiness
        loadView.value = false
    }

    fun onSearchTextChanged(query: CharSequence) {
        this.mQuery = query.toString()
    }

    fun onSelectItem(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        mSelectedPos = pos
    }

    fun onSearchClicked() {
        // call search service call.
        if (isStringEmpty(mQuery)) {
            loadView.value = true

            val type = getMethod(mSelectedPos)
            callSearchService(type, mQuery, type)
        }
    }

    private fun getMethod(pos: Int): String? {
        when (pos) {
            0 -> return SearchType.ALBUM.key
            1 -> return SearchType.ARTIST.key
            2 -> return SearchType.TRACK.key
        }
        return null
    }


    private fun isStringEmpty(str: String?): Boolean {
        return str != null && str != ""

    }

    /**
     * call search service
     * @param method - method like album/artist/track.search
     * @param query - search text
     * @param type - album/artist/track
     */
    private fun callSearchService(
        method: String?,
        query: String,
        type: String?
    ) {
        method?.let { type?.let { it1 ->
            mSearchBusiness!!.searchResult(it, query,
                it1, AppSettings.API_KEY, AppSettings.FORMAT,onSuccess,onError)
        } }
    }

    var onSuccess : (List<CommonResult>?) -> Unit = {
        loadView.value = false
        mCommonResult.value = it
    }

    var onError : (String) -> Unit = {
        loadView.value = false
        mError.value = it
    }

}
