package com.kiri.trip.data.models

import com.google.gson.annotations.SerializedName

data class PremiumData(

    @field:SerializedName("is_premium")
    val isPremium: Boolean? = null
)
