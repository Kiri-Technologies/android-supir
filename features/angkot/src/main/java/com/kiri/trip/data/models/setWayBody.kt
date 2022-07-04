package com.kiri.trip.data.models

import com.google.gson.annotations.SerializedName

data class setWayBody(
    @field:SerializedName("angkot_id")
    val angkotId: String,

    @field:SerializedName("arah")
    val arah: String,

    @field:SerializedName("is_beroperasi")
    val isBeroperasi: String,

    @field:SerializedName("route_id")
    val routeId: String
)
