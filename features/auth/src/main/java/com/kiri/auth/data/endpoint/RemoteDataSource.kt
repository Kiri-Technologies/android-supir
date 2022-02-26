package com.kiri.auth.data.endpoint

import com.kiri.auth.data.response.LoginResponse
import retrofit2.Response

class RemoteDataSource(private val auth: AuthEndpoint) : AuthEndpoint {
    override suspend fun login(email: String, password: String): Response<LoginResponse> {
        return auth.login(email, password)
    }
}
