package com.kiri.account.domain.usecase.model

import com.google.gson.annotations.SerializedName
import com.kiri.account.data.models.ProfileData

data class ProfDom(
    val image: String = "",

    @field:SerializedName("birthdate")
    val birthdate: String,

    @field:SerializedName("role")
    val role: String,

    @field:SerializedName("phone_number")
    val phone: String,

    @field:SerializedName("updated_at")
    val updatedAt: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("email")
    val email: String
) {
    constructor(data: ProfileData) : this(
        data.image ?: "",
        data.birthdate ?: "",
        data.role ?: "",
        data.phone ?: "",
        data.updatedAt ?: "",
        data.name ?: "",
        data.createdAt ?: "",
        data.id ?: "",
        data.email ?: ""
    )
}
