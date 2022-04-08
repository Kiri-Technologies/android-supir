package com.kiri.trip.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Vehicle(

    @field:SerializedName("kir_bulanan")
    val kirBulanan: String? = null,

    @field:SerializedName("route_id")
    val routeId: Int? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("is_beroperasi")
    val isBeroperasi: Int? = null,

    @field:SerializedName("deleted_at")
    val deletedAt: String? = null,

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
