package com.kiri.trip.data.models

data class AngkotDistanceData(
    val angkot_id: String = "",
    val angkot_id_didepan: String = "",
    val jarak_antar_angkot_km: Double = 0.0,
    val jarak_antar_angkot_waktu: Int = 0
)
