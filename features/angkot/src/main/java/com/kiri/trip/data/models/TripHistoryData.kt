package com.kiri.trip.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TripHistoryData(

    @field:SerializedName("is_done")
    val isDone: Int? = null,

    @field:SerializedName("jarak")
    val jarak: String? = null,

    @field:SerializedName("is_connected_with_driver")
    val isConnectedWithDriver: Int? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("nama_tempat_turun")
    val namaTempatTurun: String? = null,

    @field:SerializedName("rekomendasi_harga")
    val rekomendasiHarga: String? = null,

    @field:SerializedName("history_id")
    val historyId: Int? = null,

    @field:SerializedName("deleted_at")
    val deletedAt: String? = null,

    @field:SerializedName("vehicle")
    val vehicle: Vehicle? = null,

    @field:SerializedName("user_supir")
    val userSupir: User? = null,

    @field:SerializedName("feedback")
    val feedback: FeedbackData? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("tempat_naik_id")
    val tempatNaikId: Int? = null,

    @field:SerializedName("supir_id")
    val supirId: String? = null,

    @field:SerializedName("penumpang_id")
    val penumpangId: String? = null,

    @field:SerializedName("tempat_turun_id")
    val tempatTurunId: Int? = null,

    @field:SerializedName("nama_tempat_naik")
    val namaTempatNaik: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("angkot_id")
    val angkotId: Int? = null,

    @field:SerializedName("user_penumpang")
    val userPenumpang: User? = null
) : Parcelable
