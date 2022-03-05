package com.kiri.account.data

import com.kiri.account.data.endpoint.RemoteDataSource
import com.kiri.common.utils.BaseApiResponse
import com.kiri.common.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AccountRepositoryImpl(private val remoteDataSource: RemoteDataSource) :
    AccountRepository,
    BaseApiResponse() {
    override fun doLogout(): Flow<Resource<Nothing>> {
        return flow {
            emit(safeApiCall { remoteDataSource.doLogout() })
        }
    }
}
