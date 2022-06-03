package com.kiri.android.data

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.kiri.common.data.model.Earning

class ChartAxisFormatter(private val list: ArrayList<Earning>) : IndexAxisValueFormatter() {
    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        val index = value.toInt()
        return if (index < list.size) {
            list[index].name
        } else {
            ""
        }
    }
}
