package com.kiri.android.widget

import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.kiri.android.data.ChartAxisFormatter
import com.kiri.common.data.model.Earning

fun BarChart.initBarChart(list: ArrayList<Earning>) {
    // hide grid lines
    this.axisLeft.setDrawGridLines(false)
    val xAxis: XAxis = this.xAxis
    xAxis.setDrawGridLines(false)
    xAxis.setDrawAxisLine(false)

    // remove right y-axis
    this.axisRight.isEnabled = false

    // remove legend
    this.legend.isEnabled = false

    // remove description label
    this.description.isEnabled = false

    // add animation
    this.animateY(3000)

    // to draw label on xAxis
    xAxis.position = XAxis.XAxisPosition.BOTTOM
    xAxis.valueFormatter = ChartAxisFormatter(list)
    xAxis.setDrawLabels(true)
    xAxis.granularity = 1f
}
