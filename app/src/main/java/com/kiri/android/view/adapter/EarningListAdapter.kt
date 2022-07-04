package com.kiri.android.view.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kiri.android.R
import com.kiri.common.utils.dateToDateMillis
import com.kiri.common.utils.toFormattedString
import com.kiri.trip.data.models.RiwayatNarikData
import java.text.SimpleDateFormat
import java.util.*

class EarningListAdapter :
    BaseQuickAdapter<RiwayatNarikData, BaseViewHolder>(R.layout.earning_list_item) {
    override fun convert(holder: BaseViewHolder, item: RiwayatNarikData) {
        val time = item.createdAt?.dateToDateMillis()
        val date = time?.toFormattedString("dd/MM/yyyy")
        val startRide = item.mulaiNarik?.substringAfter(" ")?.substring(0, 5)
        val endRide = item.selesaiNarik?.substringAfter(" ")?.substring(0, 5)

        val inFormat = SimpleDateFormat("dd/MM/yyyy", Locale.forLanguageTag("ID"))
        val dayFormat: Date = inFormat.parse(date)
        val outFormat = SimpleDateFormat("EEEE", Locale.forLanguageTag("ID"))
        val day: String = outFormat.format(dayFormat)

        holder.setText(R.id.tvDate, date)
            .setText(R.id.tvClock, "$startRide - $endRide")
            .setText(R.id.tvDay, day)
    }
}
