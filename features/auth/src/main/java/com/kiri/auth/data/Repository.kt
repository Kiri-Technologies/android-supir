package com.kiri.auth.data

import com.kiri.auth.data.models.LoginData
import com.kiri.auth.data.models.RegisterBody
import com.kiri.auth.data.models.RegisterData
import com.kiri.common.utils.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun login(email: String, password: String): Flow<Resource<LoginData?>>
    suspend fun register(body: RegisterBody): Flow<Resource<RegisterData?>>
}
