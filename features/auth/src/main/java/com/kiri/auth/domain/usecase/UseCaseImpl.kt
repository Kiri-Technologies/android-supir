package com.kiri.auth.domain.usecase

import com.kiri.auth.domain.models.LoginDomain
import com.kiri.common.Resource
import kotlinx.coroutines.flow.Flow

interface UseCaseImpl {
    suspend fun login(email: String, password: String): Flow<LoginDomain>
}
