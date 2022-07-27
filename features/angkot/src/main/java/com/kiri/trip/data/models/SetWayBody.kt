package com.kiri.trip.data.models

import com.google.gson.annotations.SerializedName

data class SetWayBody(
    @field:SerializedName("angkot_id")
    val angkotId: String,

    @field:SerializedName("arah")
    val arah: String,

    @field:SerializedName("is_beroperasi")
    val isBeroperasi: Boolean,

    @field:SerializedName("route_id")
    val routeId: String
)
