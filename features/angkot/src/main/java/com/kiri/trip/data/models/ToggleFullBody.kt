package com.kiri.trip.data.models

import com.google.gson.annotations.SerializedName

data class ToggleFullBody(
    @field:SerializedName("angkot_id")
    val angkotId: String? = null,

    @field:SerializedName("is_full")
    val isFull: String? = null
)
