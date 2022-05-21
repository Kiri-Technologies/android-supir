package com.kiri.trip.data.models

import com.google.gson.annotations.SerializedName

data class EarningsByTodayData(

    @field:SerializedName("1")
    val day1: Int? = null,

    @field:SerializedName("2")
    val day2: Int? = null,

    @field:SerializedName("3")
    val day3: Int? = null,

    @field:SerializedName("4")
    val day4: Int? = null,

    @field:SerializedName("5")
    val day5: Int? = null,

    @field:SerializedName("6")
    val day6: Int? = null,

    @field:SerializedName("7")
    val day7: Int? = null
)
