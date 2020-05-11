package com.lbg.lbgtest.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import ccom.lbg.lbgtest.viewmodel.TestFMFragmentViewModel

import com.lbg.lbgtest.business.SearchBusiness
import com.lbg.lbgtest.common.AppSettings
import com.lbg.lbgtest.enums.SearchType
import com.lbg.lbgtest.model.CommonResult
import com.nhaarman.mockitokotlin2.never
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
@RunWith(JUnit4::class)
class FragmentViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var mSearchBusiness : SearchBusiness

    private lateinit var mViewModel: TestFMFragmentViewModel

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        mViewModel = TestFMFragmentViewModel()
        mViewModel.setUp(mSearchBusiness)
        Assert.assertEquals(false, mViewModel.loadView.value)
    }

    @Test
    fun `Test onSearchChanged`(){
        mViewModel.onSearchTextChanged("Hello")
        Assert.assertEquals("Hello", mViewModel.mQuery)
    }

    @Test
    fun `Verify onSearchResult method is being called`(){
        mViewModel.mQuery = "album"
        mViewModel.mSelectedPos = 0
        mViewModel.onSearchClicked()
        Mockito.verify(mSearchBusiness).searchResult(
            SearchType.ALBUM.key, "album", SearchType.ALBUM.key,
            AppSettings.API_KEY, AppSettings.FORMAT, mViewModel.onSuccess, mViewModel.onError
        )
    }

    @Test
    fun `Never verify onSearchResult method when search text is empty or null`(){
        mViewModel.mQuery = ""
        mViewModel.mSelectedPos = 0
        mViewModel.onSearchClicked()
        Mockito.verify(mSearchBusiness, never()).searchResult(
            SearchType.ALBUM.key, "album", SearchType.ALBUM.key,
            AppSettings.API_KEY, AppSettings.FORMAT, mViewModel.onSuccess, mViewModel.onError
        )
    }


    @Test
    fun `Test onSuccess`(){
        val commonResult = CommonResult("Cher", "", "12345", "Myth", "", 0)
        val commonResultList = ArrayList<CommonResult>()
        commonResultList.add(commonResult)
        mViewModel.onSuccess(commonResultList)
        val result = mViewModel.mCommonResult.value
        Assert.assertEquals(1, result!!.size)
    }

    @Test
    fun `Test onError`(){
        mViewModel.onError("IO Exception")
        val error = mViewModel.mError.value
        Assert.assertEquals("IO Exception", error)
    }
}