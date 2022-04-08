package com.kiri.android.view.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kiri.android.R
import com.kiri.common.utils.dateToDateMillis
import com.kiri.common.utils.formatRupiah
import com.kiri.common.utils.toFormattedString
import com.kiri.trip.data.models.RiwayatNarikData

class RideHistoryDetailAdapter :
    BaseQuickAdapter<RiwayatNarikData, BaseViewHolder>(R.layout.board_history_item) {
    override fun convert(holder: BaseViewHolder, item: RiwayatNarikData) {
        val time = item.createdAt?.dateToDateMillis()
        val date = time?.toFormattedString("dd/MM/yyyy")
        val startRide = item.mulaiNarik?.substringAfter(" ")?.substring(0, 5)
        val endRide = item.selesaiNarik?.substringAfter(" ")?.substring(0, 5)
        val earnings = item.jumlahPendapatan?.toLong()?.formatRupiah()

        holder.setText(R.id.tvDate, date)
            .setText(R.id.tvClock, "$startRide - $endRide")
            .setText(R.id.tvEarnings, earnings)
    }
}
