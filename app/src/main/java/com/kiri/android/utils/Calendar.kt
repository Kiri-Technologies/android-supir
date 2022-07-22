package com.kiri.android.utils

import java.text.SimpleDateFormat
import java.util.*

val outFormat = SimpleDateFormat("EEEE", Locale.forLanguageTag("ID"))

fun day1(): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_MONTH, -1)
    return outFormat.format(calendar.time)
}

fun day2(): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_MONTH, -2)
    return outFormat.format(calendar.time)
}

fun day3(): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_MONTH, -3)
    return outFormat.format(calendar.time)
}

fun day4(): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_MONTH, -4)
    return outFormat.format(calendar.time)
}

fun day5(): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_MONTH, -5)
    return outFormat.format(calendar.time)
}

fun day6(): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_MONTH, -6)
    return outFormat.format(calendar.time)
}

fun now(): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_MONTH, 0)
    return outFormat.format(calendar.time)
}
