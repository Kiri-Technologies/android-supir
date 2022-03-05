package com.kiri.auth.data.models

import com.google.gson.annotations.SerializedName
import com.kiri.auth.domain.usecase.LoginDomain

data class LoginData(

    @field:SerializedName("image")
    val image: Any? = null,

    @field:SerializedName("birthdate")
    val birthdate: String? = null,

    @field:SerializedName("role")
    val role: String? = null,

    @field:SerializedName("no_hp")
    val noHp: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("token")
    val tokenData: TokenData? = null
) {
    fun toDomainLogin(): LoginDomain = LoginDomain(
        image ?: "",
        birthdate.orEmpty(),
        role.orEmpty(),
        noHp.orEmpty(),
        updatedAt.orEmpty(),
        name.orEmpty(),
        createdAt.orEmpty(),
        id.orEmpty(),
        email.orEmpty(),
        tokenData ?: TokenData(Any())
    )
}
