package com.lbg.lbgtest.business


import com.lbg.lbgtest.NetworkInterface.RetrofitHelper
import com.lbg.lbgtest.apiInterface.SearchService
import com.lbg.lbgtest.enums.SearchType
import com.lbg.lbgtest.model.*

import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * Name - SearchBusiness
 * Purpose - This is to communicate with service and update the same back to view model
 */
class SearchBusiness(private val mRetrofitHelper: RetrofitHelper) {


    /**
     * Make service call to fetch the search result
     * @param method - method to search
     * @param search - search text
     * @param searchType - type of search
     * @param apiKey - api key
     * @param format - format like json/xml
     */
    fun searchResult(
        method: String,
        search: String,
        searchType: String,
        apiKey: String,
        format: String,
        onSuccess: (List<CommonResult>?) -> Unit,
        onError: (String) -> Unit
    ) {
        val searchService = mRetrofitHelper.createService(SearchService::class.java)
        searchService.getSearchResult(getQueryMap(method, search, searchType, apiKey, format))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<SearchResult> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(searchResult: SearchResult) {
                    onSuccess(handleResult(searchResult.result, searchType))
                }

                override fun onError(e: Throwable) {
                    onError(e.message!!)
                }

                override fun onComplete() {

                }
            })

    }

    /**
     * Process the Album result
     * @param albumMatcher -album matcher
     * @return - common data model
     */
    private fun processAlbumMatcher(albumMatcher: AlbumMatcher): List<CommonResult> {
        val commonResultList = ArrayList<CommonResult>()
        val albums = albumMatcher.albums
        if (albums.isNotEmpty()) {
            albums.forEach {
                commonResultList.add(CommonResult(
                    it.name, it.url, "",
                    it.artist, it.imageList[1].text, 0
                ))
            }
        }
        return commonResultList
    }

    /**
     * Process thhe artist result
     * @param artistMatcher -artist matcher
     * @return - common data model
     */
    private fun processArtistMatcher(artistMatcher: ArtistMatcher): List<CommonResult> {
        val commonResultList = ArrayList<CommonResult>()
        val artists = artistMatcher.artist
        if (artists.isNotEmpty()) {
            artists.forEach  {
                commonResultList.add(CommonResult(it.name,it.url, it.listeners,"" ,it.imageList[1].text, 1
                ))
            }
        }
        return commonResultList
    }

    /**
     * Process the track Matcher
     * @param trackMatcher - Track matcher
     * @return - common data model
     */
    private fun processTrackMatcher(trackMatcher: TrackMatcher): List<CommonResult> {
        val commonResultList = ArrayList<CommonResult>()
        val tracks = trackMatcher.track
        if (tracks.isNotEmpty()) {
            tracks.forEach{
                commonResultList.add(CommonResult(
                    it.name, it.url,
                    it.listeners, it.artist, it.imageList[1].text, 2
                ))
            }
        }
        return commonResultList
    }

    /**
     * Handle the response from service call
     * @param result -  result
     */
    private fun handleResult(
        result: Result,
        type: String
    ):List<CommonResult>?{
        // process the result and put into common result to render it on UI
        when (SearchType.valueOf(type.toUpperCase(Locale.getDefault()))) {
            SearchType.ALBUM -> return processAlbumMatcher(result.albumMatcher!!)
            SearchType.ARTIST -> return processArtistMatcher(result.artistMatcher!!)
            SearchType.TRACK -> return processTrackMatcher(result.trackMatcher!!)
            else -> //do nothing
            {
                return null;
            }
        }

    }


    /**
     * Map all the option into Query map
     * @param method - method like album/artist/track.search
     * @param search - search query
     * @param searchType - type like album/artist/track
     * @param apiKey - api key
     * @param format - json/xml
     * @return - map
     */

    private fun getQueryMap(
        method: String,
        search: String,
        searchType: String,
        apiKey: String,
        format: String
    ): Map<String, String> {
        val map = HashMap<String, String>()
        map[SearchType.METHOD.key] = "$method.search"
        map[searchType] = search
        map[SearchType.API_KEY.key] = apiKey
        map[SearchType.FORMAT.key] = format
        return map
    }
}
