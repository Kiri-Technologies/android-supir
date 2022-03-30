package com.kiri.android.view.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kiri.android.R
import com.kiri.trip.data.models.AngkotData

class ListAngkotAdapter : BaseQuickAdapter<AngkotData, BaseViewHolder>(R.layout.list_angkot_item) {
    override fun convert(holder: BaseViewHolder, item: AngkotData) {
        holder.setText(R.id.tvVehicleId, item.platNomor)
            .setText(R.id.tvTripRoute, "${item.route?.titikAwal} - ${item.route?.titikAkhir}")
    }
}
