package com.kiri.auth.data.models

import com.google.gson.annotations.SerializedName

data class RegisterBody(

    @field:SerializedName("password")
    val password: String? = null,

    @field:SerializedName("birthdate")
    val birthdate: String? = null,

    @field:SerializedName("role")
    val role: String? = null,

    @field:SerializedName("phone_number")
    val phone: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("email")
    val email: String? = null
)
