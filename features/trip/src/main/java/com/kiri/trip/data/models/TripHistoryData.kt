package com.kiri.trip.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TripHistoryData(

    @field:SerializedName("titik_turun")
    val titikTurun: String? = null,

    @field:SerializedName("is_done")
    val isDone: Int? = null,

    @field:SerializedName("jarak")
    val jarak: String? = null,

    @field:SerializedName("is_connected_with_driver")
    val isConnectedWithDriver: Int? = null,

    @field:SerializedName("titik_naik")
    val titikNaik: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("rekomendasi_harga")
    val rekomendasiHarga: String? = null,

    @field:SerializedName("vehicle")
    val vehicle: Vehicle? = null,

    @field:SerializedName("user_supir")
    val userSupir: UserSupir? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("supir_id")
    val supirId: String? = null,

    @field:SerializedName("penumpang_id")
    val penumpangId: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("angkot_id")
    val angkotId: Int? = null,

    @field:SerializedName("user_penumpang")
    val userPenumpang: UserPenumpang? = null
) : Parcelable

@Parcelize
data class UserSupir(

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("birthdate")
    val birthdate: String? = null,

    @field:SerializedName("role")
    val role: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("phone_number")
    val phoneNumber: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("email")
    val email: String? = null
) : Parcelable

@Parcelize
data class Vehicle(

    @field:SerializedName("kir_bulanan")
    val kirBulanan: String? = null,

    @field:SerializedName("route_id")
    val routeId: Int? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("is_beroperasi")
    val isBeroperasi: Double? = null,

    @field:SerializedName("route")
    val route: Route? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("user_id")
    val userId: String? = null,

    @field:SerializedName("pajak_stnk")
    val pajakStnk: String? = null,

    @field:SerializedName("supir_id")
    val supirId: String? = null,

    @field:SerializedName("qr_code")
    val qrCode: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("plat_nomor")
    val platNomor: String? = null,

    @field:SerializedName("pajak_tahunan")
    val pajakTahunan: String? = null,

    @field:SerializedName("status")
    val status: String? = null
) : Parcelable

@Parcelize
data class Route(

    @field:SerializedName("titik_awal")
    val titikAwal: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("kode_angkot")
    val kodeAngkot: Int? = null,

    @field:SerializedName("titik_akhir")
    val titikAkhir: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: Int? = null
) : Parcelable

@Parcelize
data class UserPenumpang(

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("birthdate")
    val birthdate: String? = null,

    @field:SerializedName("role")
    val role: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("phone_number")
    val phoneNumber: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("email")
    val email: String? = null
) : Parcelable
