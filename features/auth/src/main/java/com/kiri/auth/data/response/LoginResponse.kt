package com.kiri.auth.data.response

import com.kiri.auth.domain.models.LoginDomain

data class LoginResponse(
    val message: String? = null,
    val accessToken: String? = null,
    val nama: String? = null,
    val role: String? = null
) {
    fun toDomain() = LoginDomain(
        accessToken = accessToken.orEmpty(),
        message = message.orEmpty(),
        nama = nama.orEmpty(),
        role = role.orEmpty()
    )
}
