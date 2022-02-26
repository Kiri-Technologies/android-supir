package com.kiri.auth.data

import com.kiri.auth.data.endpoint.RemoteDataSource
import com.kiri.auth.data.response.LoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Repository(private val remoteDataSource: RemoteDataSource) : RepositoryImpl {
    override suspend fun login(email: String, password: String): Flow<LoginResponse> {
        return flow {
            remoteDataSource.login(email, password)
        }
    }
}
