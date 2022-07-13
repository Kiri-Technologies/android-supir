package com.kiri.trip.data.models

data class UserAngkot(
    val angkot_id: Int = 0,
    val id_perjalanan: Int = 0,
    val id_titik_naik: Int = 0,
    val id_titik_turun: Int = 0,
    val id_user: String = "",
    val titik_naik: String = "",
    val titik_turun: String = ""
) {
    override fun toString(): String {
        return "$id_titik_naik $id_user $titik_naik"
    }
}
