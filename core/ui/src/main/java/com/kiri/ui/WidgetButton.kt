package com.kiri.ui

fun disableBtn(view: ButtonView) {
    view.isEnabled = false
    view.setLoading(true)
}

fun enableBtn(view: ButtonView) {
    view.isEnabled = true
    view.setLoading(false)
}
