package com.kiri.android.view.adapter

import android.widget.Button
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kiri.android.R

class EarningsNoteAdapter() :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.earnings_note_item) {
    override fun convert(holder: BaseViewHolder, item: String) {
        holder.getView<Button>(R.id.btn).apply {
            text = item
            tag = item
        }
    }
}
