package com.kiri.trip.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AngkotData(

    @field:SerializedName("kir_bulanan")
    val kirBulanan: String? = null,

    @field:SerializedName("route_id")
    val routeId: Int? = null,

    @field:SerializedName("user_owner")
    val userOwner: UserOwner? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("is_beroperasi")
    val isBeroperasi: String? = null,

    @field:SerializedName("route")
    val route: RouteAngkot? = null,

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
data class UserOwner(

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
data class RouteAngkot(

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
