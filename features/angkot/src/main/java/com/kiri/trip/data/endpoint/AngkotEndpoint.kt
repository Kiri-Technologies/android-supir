package com.kiri.trip.data.endpoint

import com.kiri.common.utils.ApiResponse
import com.kiri.trip.data.models.AngkotData
import com.kiri.trip.data.models.FeedbackData
import com.kiri.trip.data.models.TripHistoryData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AngkotEndpoint {

    @GET("perjalanan/find")
    suspend fun getTripHistory(
        @Query("supir_id") sopirId: String
    ): Response<ApiResponse<List<TripHistoryData>>>

    @GET("perjalanan/find")
    suspend fun getTripByAngkot(
        @Query("angkot_id") angkotId: String
    ): Response<ApiResponse<List<TripHistoryData>>>

    @GET("feedback/{trip_id}")
    suspend fun getFeedbackByTripId(
        @Path("trip_id") tripId: String
    ): Response<ApiResponse<FeedbackData>>

    @GET("angkot/find")
    suspend fun getAngkot(
        @Query("owner_id") supirId: String
    ): Response<ApiResponse<List<AngkotData>>>
}
