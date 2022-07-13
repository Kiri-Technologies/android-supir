package com.kiri.android.view.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kiri.android.R
import com.kiri.trip.data.models.UserAngkot

class UserRideAdapter : BaseQuickAdapter<UserAngkot, BaseViewHolder>(R.layout.rv_string_item) {
    override fun convert(holder: BaseViewHolder, item: UserAngkot) {
        holder.setText(R.id.text, item.titik_naik)
    }
}
