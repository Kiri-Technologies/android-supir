package com.kiri.ui

import android.view.View

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
