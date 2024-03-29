package com.kiri.android.view.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kiri.android.R
import com.kiri.trip.data.models.AngkotConfirmData
import com.kiri.ui.visible

class ListAngkotAdapter :
    BaseQuickAdapter<AngkotConfirmData, BaseViewHolder>(R.layout.list_angkot_item) {
    override fun convert(holder: BaseViewHolder, item: AngkotConfirmData) {
        holder.setText(R.id.tvVehicleId, item.vehicle?.platNomor)
            .setText(
                R.id.tvTripRoute,
                "${item.vehicle?.route?.titikAwal} - ${item.vehicle?.route?.titikAkhir}"
            )
        if (item.vehicle?.isBeroperasi == 1) holder.getView<TextView>(R.id.tvIsOperated).visible()
    }
}
