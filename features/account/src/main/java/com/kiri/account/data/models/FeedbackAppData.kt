package com.kiri.account.data.models

import com.google.gson.annotations.SerializedName

data class FeedbackAppData(

    @field:SerializedName("tanggapan")
    val tanggapan: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("user_id")
    val userId: String? = null,

    @field:SerializedName("review")
    val review: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("status")
    val status: String? = null
)
