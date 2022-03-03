package com.kiri.auth.data.models

import com.google.gson.annotations.SerializedName

data class OriginalData(

    @field:SerializedName("token_type")
    val tokenType: String? = null,

    @field:SerializedName("expires_in")
    val expiresIn: Int? = null,

    @field:SerializedName("token")
    val token: String? = null
)
