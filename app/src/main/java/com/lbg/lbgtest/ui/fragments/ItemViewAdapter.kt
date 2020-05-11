package com.lbg.lbgtest.ui.fragments


import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView

import com.lbg.lbgtest.BR
import com.lbg.lbgtest.databinding.ItemViewBinding
import com.lbg.lbgtest.model.CommonResult
import com.lbg.lbgtest.viewmodel.ItemAdapterViewModel

/**
 * Name - Item view Adapter
 * Purpose - Bind the item to Recycler view
 */
class ItemViewAdapter(
    @param:LayoutRes private val layoutId: Int, private var dataSet: List<CommonResult>?,
    private val viewModel: ItemAdapterViewModel,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<ItemViewAdapter.GenericViewHolder>() {

    init {
        subscribeObserver()
    }

    private fun subscribeObserver() {
        viewModel.url.observeForever(Observer<String> { url ->
            if (!TextUtils.isEmpty(url)) {
                onItemClickListener.onClicked(url)
                viewModel.url.value = null
            }
        })
    }

    private fun getLayoutIdForPosition(position: Int): Int {
        return layoutId
    }

    override fun getItemCount(): Int {
        return if (dataSet == null) 0 else dataSet!!.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ItemViewBinding>(layoutInflater, viewType, parent, false)
        return GenericViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenericViewHolder, position: Int) {
        holder.bind(position)

    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    fun setDataSet(data: List<CommonResult>) {
        this.dataSet = data
    }

    inner class GenericViewHolder(private val binding: ItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int?) {
            binding.setVariable(BR.dataModel, dataSet!![position!!])
            binding.setVariable(BR.viewModel, viewModel)
            binding.executePendingBindings()
        }

    }

    /**
     * Item click on Adapter view
     */
    interface OnItemClickListener {
        fun onClicked(url: String)
    }
}
