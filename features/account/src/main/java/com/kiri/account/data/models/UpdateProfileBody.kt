package com.kiri.account.data.models

import com.google.gson.annotations.SerializedName

data class UpdateProfileBody(

    @field:SerializedName("birthdate")
    val birthdate: String? = null,

    @field:SerializedName("role")
    val role: String? = null,

    @field:SerializedName("phone_number")
    val noHp: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("email")
    val email: String? = null
)
