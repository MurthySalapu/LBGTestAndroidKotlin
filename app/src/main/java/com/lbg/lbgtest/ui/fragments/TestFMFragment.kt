package com.lbg.lbgtest.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import ccom.lbg.lbgtest.viewmodel.TestFMFragmentViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.lbg.lbgtest.BR
import com.lbg.lbgtest.NetworkInterface.RetrofitHelper
import com.lbg.lbgtest.R
import com.lbg.lbgtest.business.SearchBusiness
import com.lbg.lbgtest.databinding.FragmentHomeBinding
import com.lbg.lbgtest.model.CommonResult
import com.lbg.lbgtest.ui.WebViewActivity
import com.lbg.lbgtest.viewmodel.ItemAdapterViewModel

import com.lbg.lbgtest.viewmodel.ViewModelProviderFactory


/**
 * Name - Test FM Fragment
 * Purpose - Search the query and load the data that gets on query
 */

class TestFMFragment : BaseFragment<FragmentHomeBinding, TestFMFragmentViewModel>(),
    ItemViewAdapter.OnItemClickListener {
    override fun onClicked(url: String) {
        if (!TextUtils.isEmpty(url)) {
            val webIntent = Intent(baseActivity, WebViewActivity::class.java)
            webIntent.putExtra("url", url)
            startActivity(webIntent)
        }
    }

    private lateinit var mViewModel: TestFMFragmentViewModel

    private var mItemViewAdapter: ItemViewAdapter? = null

    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.fragment_home

    override val viewModel: TestFMFragmentViewModel
        get() {
            mViewModel = ViewModelProviders.of(this, ViewModelProviderFactory(TestFMFragmentViewModel()))
                .get(TestFMFragmentViewModel::class.java)
            return mViewModel
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.setUp(SearchBusiness(RetrofitHelper()))
        subscribeObserver()
    }

    /**
     * Subscribe observer to listen the events
     */
    private fun subscribeObserver() {
        mViewModel.mCommonResult
            .observe(this, Observer<List<CommonResult>> { commonResult -> loadAdapter(commonResult) })
        mViewModel.mError.observe(this, Observer<String> { s -> displayToast(s) })
    }

    /**
     * Display toast for error
     * @param error - error
     */
    private fun displayToast(error: String) {
        Toast.makeText(baseActivity, "Error:$error", Toast.LENGTH_LONG).show()
    }

    /**
     * load the adapter with content
     * @param commonResults - commonResults
     */
    private fun loadAdapter(commonResults: List<CommonResult>) {
        mItemViewAdapter?.let {
            it.setDataSet(commonResults)
            it.notifyDataSetChanged()
        } ?: run {
            mItemViewAdapter =
                ItemViewAdapter(R.layout.item_view, commonResults, ItemAdapterViewModel(), this)
            mViewDataBinding.recyclerView.adapter = mItemViewAdapter
            mViewDataBinding.recyclerView.layoutManager = LinearLayoutManager(baseActivity)
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("profileImage")
        fun loadImage(view: ImageView, imageUrl: String) {
            if (!TextUtils.isEmpty(imageUrl)) {
                Glide.with(view.context)
                    .load(imageUrl).apply(RequestOptions().circleCrop())
                    .into(view)
            }
        }
    }
}
