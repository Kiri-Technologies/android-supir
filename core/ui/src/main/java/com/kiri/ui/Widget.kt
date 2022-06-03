package com.kiri.ui

import android.content.Context
import android.content.res.Resources
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.Button
import androidx.core.content.res.ResourcesCompat
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

fun disableBtn(view: ButtonView) {
    view.isEnabled = false
    view.setLoading(true)
}

fun enableBtn(view: ButtonView) {
    view.isEnabled = true
    view.setLoading(false)
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun Context.selectedBtn(btn: Button) {
    val theme: Resources.Theme = ContextThemeWrapper(
        this,
        R.style.Theme_Kiri
    ).theme
    btn.setBackgroundColor(
        ResourcesCompat.getColor(
            resources,
            R.color.blue_color,
            theme
        )
    )
    btn.setTextColor(
        ResourcesCompat.getColor(
            resources,
            R.color.white,
            theme
        )
    )
}

fun Context.unSelectedBtn(btn: Button) {
    val theme: Resources.Theme = ContextThemeWrapper(
        this,
        R.style.Theme_Kiri
    ).theme
    btn.setBackgroundColor(
        ResourcesCompat.getColor(
            resources,
            R.color.white,
            theme
        )
    )
    btn.setTextColor(
        ResourcesCompat.getColor(
            resources,
            R.color.black,
            theme
        )
    )
}

