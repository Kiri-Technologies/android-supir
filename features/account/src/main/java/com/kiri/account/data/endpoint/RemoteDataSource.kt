package com.kiri.account.data.endpoint

import com.kiri.common.utils.ApiResponse
import retrofit2.Response

class RemoteDataSource(private val accountEndpoint: AccountEndpoint) : AccountEndpoint {
    override suspend fun doLogout(): Response<ApiResponse<Nothing>> {
        return accountEndpoint.doLogout()
    }
}
