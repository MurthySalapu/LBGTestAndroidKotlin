package com.lbg.lastfm.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lbg.lbgtest.model.CommonResult
import com.lbg.lbgtest.viewmodel.ItemAdapterViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ItemAdapterViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mViewModel: ItemAdapterViewModel

    @Before
    fun setUp() {
        mViewModel = ItemAdapterViewModel()
    }


    @Test
    fun `Test card item clicked`() {
        mViewModel.onCardItemClicked("https://www.google.com")
        Assert.assertEquals("https://www.google.com", mViewModel.url.value)
    }


    @Test
    fun `Test getData method for Album`() {
        val commonResult = CommonResult("cher", "https://url", "12345", "Bob", "", 0)
        Assert.assertEquals(mViewModel.getData(commonResult), "Artist:Bob")
    }


    @Test
    fun `Test getData method for artist`() {
        val commonResult = CommonResult("cher", "https://url", "12345", "Bob", "", 1)
        Assert.assertEquals(mViewModel.getData(commonResult), "Listeners:12345")
    }


    @Test
    fun `Test getData Method for track`() {
        val commonResult = CommonResult("cher", "https://url", "12345", "Bob", "", 2)
        Assert.assertEquals(mViewModel.getData(commonResult), "Listeners:12345")
    }


    @Test
    fun `Test getData method for nothing`() {
        val commonResult = CommonResult("cher", "https://url", "12345", "Bob", "", 3)
        Assert.assertNull(mViewModel.getData(commonResult))
    }
}