package com.kiri.trip.data.endpoint

import com.kiri.common.utils.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TripEndpoint {

    @GET("perjalanan/find/{supir_id}")
    suspend fun getTripHistory(
        @Path("supir_id") sopirId: String
    ): Response<ApiResponse<Nothing>>
}
