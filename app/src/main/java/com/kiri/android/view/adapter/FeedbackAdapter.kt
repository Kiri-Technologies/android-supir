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
        item.feedback?.let {
            val time = it.createdAt?.dateToDateMillis()
            val date = time?.toFormattedString("dd/MM/yy")
            holder.setText(R.id.tvDate, date)

            holder.getView<RatingBar>(R.id.rating).rating =
                it.rating?.toFloatOrNull() ?: 0.toFloat()
            holder.setText(R.id.tvComment, item.feedback?.komentar)
        }
    }

    init {
        setEmptyView(R.layout.empty_view_item)
    }
}
