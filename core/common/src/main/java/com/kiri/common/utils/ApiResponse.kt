package com.kiri.common.utils

import com.google.gson.annotations.SerializedName

data class ApiResponse<out T>(

    @field:SerializedName("data")
    val dataData: T? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)
