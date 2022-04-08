package com.kiri.trip.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RiwayatNarikData(

    @field:SerializedName("jumlah_pendapatan")
    val jumlahPendapatan: Int? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("user_id")
    val userId: String? = null,

    @field:SerializedName("mulai_narik")
    val mulaiNarik: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("supir")
    val supir: User? = null,

    @field:SerializedName("deleted_at")
    val deletedAt: String? = null,

    @field:SerializedName("angkot_id")
    val angkotId: Int? = null,

    @field:SerializedName("selesai_narik")
    val selesaiNarik: String? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("vehicle")
    val vehicle: Vehicle? = null
) : Parcelable
