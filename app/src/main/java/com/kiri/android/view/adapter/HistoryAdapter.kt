package com.kiri.android.view.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kiri.android.R
import com.kiri.common.utils.dateToDateMillis
import com.kiri.common.utils.toFormattedString
import com.kiri.trip.data.models.TripHistoryData

class HistoryAdapter :
    BaseQuickAdapter<TripHistoryData, BaseViewHolder>(R.layout.history_trip_layout) {
    override fun convert(holder: BaseViewHolder, item: TripHistoryData) {
        val route = "${item.vehicle?.route?.titikAwal} - ${item.vehicle?.route?.titikAkhir}"
        holder.setText(R.id.tvTripRoute, route)
            .setText(R.id.tvBoard, item.titikNaik)
            .setText(R.id.tvDrop, item.titikTurun)
            .getView<TextView>(R.id.tvTripRoute).isSelected = true

        val time = item.createdAt?.dateToDateMillis()
        val date = time?.toFormattedString("dd/MM/yyyy")
        val clock = time?.toFormattedString("HH.mm a")

        holder.setText(R.id.tvDate, "$date - $clock")
    }
}
