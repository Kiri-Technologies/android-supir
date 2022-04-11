package com.kiri.android.view.adapter

import android.widget.RatingBar
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kiri.android.R
import com.kiri.common.utils.dateToDateMillis
import com.kiri.common.utils.toFormattedString
import com.kiri.trip.data.models.TripHistoryData

class FeedbackDetailAdapter :
    BaseQuickAdapter<TripHistoryData, BaseViewHolder>(R.layout.feedback_detail_item) {
    override fun convert(holder: BaseViewHolder, item: TripHistoryData) {
        val time = item.feedback?.createdAt?.dateToDateMillis()
        val date = time?.toFormattedString("dd/MM/yyyy")
        val clock = time?.toFormattedString("HH.mm a")

        holder.setText(R.id.tvUser, item.userPenumpang?.name)
            .setText(R.id.tvDateAndClock, "$date - $clock")
            .setText(R.id.tvComment, item.feedback?.komentar)
        holder.getView<RatingBar>(R.id.rating).rating =
            item.feedback?.rating?.toFloatOrNull() ?: 0.toFloat()
    }
}
