package com.kiri.trip.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AngkotConfirmData(

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("is_confirmed")
    val isConfirmed: Int? = null,

    @field:SerializedName("supir_id")
    val supirId: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("user")
    val user: User? = null,

    @field:SerializedName("angkot_id")
    val angkotId: Int? = null,

    @field:SerializedName("vehicle")
    val vehicle: Vehicle? = null
) : Parcelable
