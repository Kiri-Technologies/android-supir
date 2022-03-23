package com.kiri.account.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class ProfileData(

    @field:SerializedName("image")
    val image: @RawValue Any? = null,

    @field:SerializedName("birthdate")
    val birthdate: String? = null,

    @field:SerializedName("role")
    val role: String? = null,

    @field:SerializedName("phone_number")
    val phone: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("email")
    val email: String? = null
) : Parcelable
