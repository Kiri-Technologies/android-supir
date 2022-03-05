package com.kiri.auth.domain.usecase

import com.kiri.auth.data.models.TokenData

data class LoginDomain(
    val image: Any,
    val birthdate: String,
    val role: String,
    val noHp: String,
    val updatedAt: String,
    val name: String,
    val createdAt: String,
    val id: String,
    val email: String,
    val tokenData: TokenData
)
