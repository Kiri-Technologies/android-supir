package com.kiri.auth.domain.models

data class LoginDomain(
    val message: String,
    val accessToken: String,
    val nama: String,
    val role: String
)
