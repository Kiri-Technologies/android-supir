package com.kiri.android.view.adapter

import android.widget.RatingBar
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kiri.android.R
import com.kiri.common.utils.dateToDateMillis
import com.kiri.common.utils.toFormattedString
import com.kiri.trip.data.models.TripHistoryData

class FeedbackAdapter : BaseQuickAdapter<TripHistoryData, BaseViewHolder>(R.layout.feedback_item) {
    override fun convert(holder: BaseViewHolder, item: TripHistoryData) {
        val time = item.feedback?.createdAt?.dateToDateMillis()
        val date = time?.toFormattedString("dd/MM/yy")
        holder.setText(R.id.tvDate, date)

        holder.getView<RatingBar>(R.id.rating).rating = item.feedback?.rating?.toFloat()!!

        val comment = "${item.feedback?.komentar?.take(30)}..."
        if (item.feedback?.komentar?.length!! >= 15) {
            holder.setText(R.id.tvComment, comment)
        } else {
            holder.setText(R.id.tvComment, item.feedback?.komentar)
        }
    }
}
