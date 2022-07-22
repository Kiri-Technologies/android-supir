package com.kiri.android.utils

import java.text.SimpleDateFormat
import java.util.*

fun currentTime(): String {
    val calendar = Calendar.getInstance()
    val outFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return outFormat.format(calendar.time)
}