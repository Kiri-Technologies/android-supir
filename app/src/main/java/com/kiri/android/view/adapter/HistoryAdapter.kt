package com.kiri.android.view.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kiri.android.R
import com.kiri.common.utils.dateToDateMillis
import com.kiri.common.utils.toFormattedString

class HistoryAdapter :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.history_trip_layout, mutableListOf()) {
    override fun convert(holder: BaseViewHolder, item: String) {
        val asd = ""

        val time = asd.dateToDateMillis()
        val date = time.toFormattedString("dd/MM/yyyy")
        val clock = time.toFormattedString("HH.mm a")
        // NOTHING
    }
}
