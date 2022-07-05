package com.kiri.trip.data.models

import com.google.gson.annotations.SerializedName

data class ToggleStopBody(
    @field:SerializedName("angkot_id")
    val angkotId: String? = null,

    @field:SerializedName("lat")
    val lat: String? = null,

    @field:SerializedName("long")
    val long: String? = null,

    @field:SerializedName("is_waiting_for_passengers")
    val waitingPassenger: String? = null
)
