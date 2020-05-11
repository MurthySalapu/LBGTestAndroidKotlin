package com.lbg.lbgtest.business

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lbg.lbgtest.NetworkInterface.RetrofitHelper
import com.lbg.lbgtest.apiInterface.SearchService
import com.lbg.lbgtest.common.AppSettings
import com.lbg.lbgtest.enums.SearchType
import com.lbg.lbgtest.model.*

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.*


import java.util.ArrayList


@RunWith(JUnit4::class)
class SearchBusinessTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var mRetrofitHelper : RetrofitHelper

    @Mock
    lateinit var  mSearchService : SearchService

    private val onSuccess = mock<(List<CommonResult>?) -> Unit>()

    private val onError = mock<(String) -> Unit>()


    private lateinit var mSearchBusiness: SearchBusiness

    companion object {
        @BeforeClass
        @JvmStatic
        fun setUpClass() {

            RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        }
    }

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        mSearchBusiness = SearchBusiness(this.mRetrofitHelper)
    }


    @Test
    fun `Test album result on search`(){
        val albumList = ArrayList<Album>()
        val albumImageList = ArrayList<AlbumImage>()

        val albumImage1 = AlbumImage("size1","Text1")
        val albumImage2 = AlbumImage("size2","Text2")
        albumImageList.add(albumImage1)
        albumImageList.add(albumImage2)
        val album = Album("Name","Artist","url",albumImageList)
        albumList.add(album)
        val albumMatcher = AlbumMatcher(albumList)
        val result = Result(albumMatcher,null,null)
        val searchResult = SearchResult(result)


        Mockito.`when`(this.mRetrofitHelper.createService(SearchService::class.java)).thenReturn(mSearchService)

        Mockito.`when`(this.mSearchService.getSearchResult(ArgumentMatchers.anyMap())).thenAnswer {
            return@thenAnswer Observable.just(searchResult)
        }

        mSearchBusiness.searchResult(
            SearchType.ALBUM.key,
            "Test",
            SearchType.ALBUM.key,
            AppSettings.API_KEY,
            AppSettings.FORMAT,
            onSuccess,
            onError
        )
        verify(onSuccess).invoke(any())
    }


    @Test
    fun `Test artist result on  search`(){

        val artistList = ArrayList<Artist>()
        val albumImageList = ArrayList<AlbumImage>()

        val albumImage1 = AlbumImage("size1","Text1")
        val albumImage2 = AlbumImage("size2","Text2")
        albumImageList.add(albumImage1)
        albumImageList.add(albumImage2)
        val artist = Artist("Name","Artist","url",albumImageList)
        artistList.add(artist)
        val artistMatcher = ArtistMatcher(artistList)
        val result = Result(null,artistMatcher,null)
        val searchResult = SearchResult(result)


        Mockito.`when`(this.mRetrofitHelper.createService(SearchService::class.java)).thenReturn(mSearchService)

        Mockito.`when`(this.mSearchService.getSearchResult(ArgumentMatchers.anyMap())).thenAnswer {
            return@thenAnswer Observable.just(searchResult)
        }

        mSearchBusiness.searchResult(
            SearchType.ARTIST.key,
            "Test",
            SearchType.ARTIST.key,
            AppSettings.API_KEY,
            AppSettings.FORMAT,
            onSuccess,
            onError
        )
        verify(onSuccess).invoke(any())
    }

    @Test
    fun `Test track result on search`(){

        val trackList = ArrayList<Track>()
        val albumImageList = ArrayList<AlbumImage>()

        val albumImage1 = AlbumImage("size1","Text1")
        val albumImage2 = AlbumImage("size2","Text2")
        albumImageList.add(albumImage1)
        albumImageList.add(albumImage2)
        val track = Track("Name","Listeners","Artist","url",albumImageList)
        trackList.add(track)
        val trackMatcher = TrackMatcher(trackList)
        val result = Result(null,null,trackMatcher)
        val searchResult = SearchResult(result)


        Mockito.`when`(this.mRetrofitHelper.createService(SearchService::class.java)).thenReturn(mSearchService)

        Mockito.`when`(this.mSearchService.getSearchResult(ArgumentMatchers.anyMap())).thenAnswer {
            return@thenAnswer Observable.just(searchResult)
        }

        mSearchBusiness.searchResult(
            SearchType.TRACK.key,
            "Test",
            SearchType.TRACK.key,
            AppSettings.API_KEY,
            AppSettings.FORMAT,
            onSuccess,
            onError
        )
        verify(onSuccess).invoke(any())
    }

    @Test
    fun `Test error result on search`(){

        val exception = Exception("Server Down.Please try again")

        Mockito.`when`(this.mRetrofitHelper.createService(SearchService::class.java)).thenReturn(mSearchService)

        Mockito.`when`(this.mSearchService.getSearchResult(ArgumentMatchers.anyMap())).thenReturn(Observable.error(exception))

        mSearchBusiness.searchResult(
            SearchType.TRACK.key,
            "Test",
            SearchType.TRACK.key,
            AppSettings.API_KEY,
            AppSettings.FORMAT,
            onSuccess,
            onError
        )

        verify(onError).invoke(any())
    }
}