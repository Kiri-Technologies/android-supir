package com.kiri.auth.domain.usecase

import com.kiri.auth.data.Repository
import com.kiri.auth.domain.models.LoginDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UseCase(private val repo: Repository) : UseCaseImpl {
    override suspend fun login(email: String, password: String): Flow<LoginDomain> {
        return repo.login(email, password).map { it.toDomain() }
    }
}
