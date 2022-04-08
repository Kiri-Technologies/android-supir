package com.kiri.android.view.adapter

import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kiri.android.R
import com.kiri.trip.data.models.AngkotConfirmData
import com.kiri.ui.visible

class AngkotConfirmationAdapter :
    BaseQuickAdapter<AngkotConfirmData, BaseViewHolder>(R.layout.list_angkot_item) {
    override fun convert(holder: BaseViewHolder, item: AngkotConfirmData) {
        holder.getView<LinearLayout>(R.id.llBtn).visible()
        holder.setText(R.id.tvVehicleId, item.vehicle?.platNomor)
            .setText(
                R.id.tvTripRoute,
                "${item.vehicle?.route?.titikAwal} - ${item.vehicle?.route?.titikAkhir}"
            )
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.addChildClickViewIds(R.id.btnCancel, R.id.btnAcc)
    }
}
