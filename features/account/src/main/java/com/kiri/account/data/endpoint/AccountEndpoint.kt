package com.kiri.account.data.endpoint

import com.kiri.common.utils.ApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface AccountEndpoint {

    @GET("logout")
    suspend fun doLogout(): Response<ApiResponse<Nothing>>
}
