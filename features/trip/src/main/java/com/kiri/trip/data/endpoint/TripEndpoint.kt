package com.kiri.trip.data.endpoint

import com.kiri.common.utils.ApiResponse
import com.kiri.trip.data.models.TripHistoryData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TripEndpoint {

    @GET("perjalanan/find")
    suspend fun getTripHistory(
        @Query("supir_id") sopirId: String
    ): Response<ApiResponse<List<TripHistoryData>>>
}
