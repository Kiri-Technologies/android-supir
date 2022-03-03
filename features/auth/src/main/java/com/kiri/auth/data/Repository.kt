package com.kiri.auth.data

import com.kiri.auth.data.endpoint.RemoteDataSource
import com.kiri.auth.data.models.LoginData
import com.kiri.auth.data.models.RegisterBody
import com.kiri.auth.data.models.RegisterData
import com.kiri.common.utils.BaseApiResponse
import com.kiri.common.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Repository(private val remoteDataSource: RemoteDataSource) : RepositoryImpl,
    BaseApiResponse() {
    override suspend fun login(
        email: String,
        password: String
    ): Flow<Resource<LoginData?>> {
        return flow {
            emit(safeApiCall { remoteDataSource.login(email, password) })
        }
    }

    override suspend fun register(body: RegisterBody): Flow<Resource<RegisterData?>> {
        return flow {
            emit(safeApiCall { remoteDataSource.register(body) })
        }
    }
}
