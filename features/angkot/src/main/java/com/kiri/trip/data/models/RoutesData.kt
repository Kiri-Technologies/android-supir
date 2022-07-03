package com.kiri.trip.data.models

import com.google.gson.annotations.SerializedName

data class RoutesData(

    @field:SerializedName("kode_trayek")
    val kodeTrayek: String? = null,

    @field:SerializedName("titik_awal")
    val titikAwal: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("titik_akhir")
    val titikAkhir: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("lat_titik_akhir")
    val latTitikAkhir: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("long_titik_awal")
    val longTitikAwal: String? = null,

    @field:SerializedName("lat_titik_awal")
    val latTitikAwal: String? = null,

    @field:SerializedName("long_titik_akhir")
    val longTitikAkhir: String? = null,

    @field:SerializedName("deleted_at")
    val deletedAt: Any? = null
)
