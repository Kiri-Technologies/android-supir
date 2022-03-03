package com.kiri.auth.data.models

import com.google.gson.annotations.SerializedName

data class TokenData(

    @field:SerializedName("exception")
    val exception: Any? = null,

    @field:SerializedName("headers")
    val headersData: HeadersData? = null,

    @field:SerializedName("original")
    val originalData: OriginalData? = null
)
