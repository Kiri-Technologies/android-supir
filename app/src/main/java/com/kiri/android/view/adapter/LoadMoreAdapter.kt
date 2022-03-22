package com.kiri.android.view.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.chad.library.adapter.base.loadmore.BaseLoadMoreView
import com.chad.library.adapter.base.util.getItemView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kiri.android.R

class LoadMoreAdapter : BaseLoadMoreView() {
    override fun getLoadComplete(holder: BaseViewHolder): View {
        TODO("Not yet implemented")
    }

    override fun getLoadEndView(holder: BaseViewHolder): View {
        TODO("Not yet implemented")
    }

    override fun getLoadFailView(holder: BaseViewHolder): View {
        TODO("Not yet implemented")
    }

    override fun getLoadingView(holder: BaseViewHolder): View =
        holder.getView<FrameLayout>(R.id.load_more_loading_view)

    override fun getRootView(parent: ViewGroup): View =
        parent.getItemView(R.layout.load_layout)
}
