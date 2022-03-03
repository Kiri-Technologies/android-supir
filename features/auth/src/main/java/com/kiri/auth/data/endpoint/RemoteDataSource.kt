package com.kiri.auth.data.endpoint

import com.kiri.auth.data.models.LoginData
import com.kiri.auth.data.models.RegisterBody
import com.kiri.auth.data.models.RegisterData
import com.kiri.common.utils.ApiResponse
import retrofit2.Response

class RemoteDataSource(private val auth: AuthEndpoint) : AuthEndpoint {

    override suspend fun login(email: String, password: String): Response<ApiResponse<LoginData>> {
        return auth.login(email, password)
    }

    override suspend fun register(registerBody: RegisterBody): Response<ApiResponse<RegisterData>> {
        return auth.register(registerBody)
    }
}
