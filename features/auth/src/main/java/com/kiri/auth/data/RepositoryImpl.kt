package com.kiri.auth.data

import com.kiri.auth.data.response.LoginResponse
import com.kiri.common.Resource
import kotlinx.coroutines.flow.Flow

interface RepositoryImpl {
    suspend fun login(email: String, password: String): Flow<LoginResponse>
}
