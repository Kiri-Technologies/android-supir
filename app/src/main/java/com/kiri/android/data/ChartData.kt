package com.kiri.android.data

import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.kiri.common.data.model.Earning

fun BarChart.chartData(list: ArrayList<Earning>) {
    // now draw bar chart with dynamic data
    val entries: ArrayList<BarEntry> = ArrayList()

    // you can replace this data object with  your custom object
    for (i in list.indices) {
        val score = list[i]
        entries.add(BarEntry(i.toFloat(), score.earnings.toFloat()))
    }

    val barDataSet = BarDataSet(entries, "")
    barDataSet.color = resources.getColor(com.kiri.android.R.color.color_primary, null)
    val data = BarData(barDataSet)
    this.data = data
    this.invalidate()
}
