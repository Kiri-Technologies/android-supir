package com.kiri.trip.data.models

import com.google.gson.annotations.SerializedName

data class LocationBody(

    @field:SerializedName("angkot_id")
    val angkotId: String? = null,

    @field:SerializedName("lat")
    val lat: String? = null,

    @field:SerializedName("long")
    val long: String? = null
)
