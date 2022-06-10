package com.kiri.android.view.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kiri.android.R

class UserRideAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.rv_string_item) {
    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.text, item)
    }
}
