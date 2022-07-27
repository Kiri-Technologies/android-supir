package com.kiri.trip.data.models

import com.google.gson.annotations.SerializedName

data class UserPremiumData(

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("user_id")
    val userId: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("from")
    val from: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("to")
    val to: String? = null,

    @field:SerializedName("payment_date")
    val paymentDate: String? = null
)
