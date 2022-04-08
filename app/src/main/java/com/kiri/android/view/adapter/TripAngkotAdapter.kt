package com.kiri.android.view.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kiri.android.R
import com.kiri.common.utils.dateToDateMillis
import com.kiri.common.utils.toFormattedString
import com.kiri.trip.data.models.TripHistoryData

class TripAngkotAdapter :
    BaseQuickAdapter<TripHistoryData, BaseViewHolder>(R.layout.trip_angkot_item) {
    override fun convert(holder: BaseViewHolder, item: TripHistoryData) {
        val time = item.createdAt?.dateToDateMillis()
        val clock = time?.toFormattedString("HH.mm")
        holder.setText(R.id.tvClock, clock)
            .setText(R.id.tvBoard, item.namaTempatNaik)
            .setText(R.id.tvDrop, item.namaTempatTurun)
    }
}
