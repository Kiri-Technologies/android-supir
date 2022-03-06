package com.kiri.auth.domain.usecase

import com.kiri.auth.data.RepositoryImpl
import com.kiri.auth.data.models.LoginData
import com.kiri.auth.data.models.RegisterBody
import com.kiri.auth.data.models.RegisterData
import com.kiri.common.utils.Resource
import kotlinx.coroutines.flow.Flow

class UseCaseImpl(private val repo: RepositoryImpl) : UseCase {
    override suspend fun login(
        email: String,
        password: String
    ): Flow<Resource<LoginData?>> {
        return repo.login(email, password)
    }

    override suspend fun register(body: RegisterBody): Flow<Resource<RegisterData?>> {
        return repo.register(body)
    }
}
