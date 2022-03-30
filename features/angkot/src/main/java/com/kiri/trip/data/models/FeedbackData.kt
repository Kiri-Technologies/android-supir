package com.kiri.trip.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FeedbackData(

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("review")
    val review: String? = null,

    @field:SerializedName("komentar")
    val komentar: String? = null,

    @field:SerializedName("rating")
    val rating: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("perjalanan_id")
    val perjalananId: Int? = null,

    @field:SerializedName("id")
    val id: Int? = null
) : Parcelable
