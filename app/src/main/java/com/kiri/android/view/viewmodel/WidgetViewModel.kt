package com.kiri.android.view.viewmodel

import androidx.lifecycle.ViewModel

class WidgetViewModel : ViewModel() {

    private var currentItem: String = ""
    fun getCurrentItem(): String = currentItem
    fun setCurrentItem(harga: String) {
        currentItem = harga
    }
}
