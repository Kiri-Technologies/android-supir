package com.kiri.trip.data.endpoint

import com.kiri.common.utils.ApiResponse
import com.kiri.trip.data.models.AngkotConfirmData
import com.kiri.trip.data.models.FeedbackData
import com.kiri.trip.data.models.RiwayatNarikData
import com.kiri.trip.data.models.TotalEarningsData
import com.kiri.trip.data.models.TripHistoryData
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AngkotEndpoint {

    @GET("perjalanan/find")
    suspend fun getTripHistory(
        @Query("supir_id") sopirId: String
    ): Response<ApiResponse<List<TripHistoryData>>>

    @GET("perjalanan/find")
    suspend fun getTripByAngkot(
        @Query("is_done") is_done: Int,
        @Query("angkot_id") angkotId: String,
        @Query("supir_id") supirId: String
    ): Response<ApiResponse<List<TripHistoryData>>>

    @GET("feedback/{trip_id}")
    suspend fun getFeedbackByTripId(
        @Path("trip_id") tripId: String
    ): Response<ApiResponse<FeedbackData>>

    @GET("ownersupir/driver")
    suspend fun getAngkot(
        @Query("supir_id") supirId: String
    ): Response<ApiResponse<List<AngkotConfirmData>>>

    @GET("ownersupir/riwayat/find")
    suspend fun getRideHistory(
        @Query("angkot_id") angkotId: String,
        @Query("supir_id") supirId: String
    ): Response<ApiResponse<List<RiwayatNarikData>>>

    @GET("ownersupir/driver")
    suspend fun getListAngkotConfirmation(
        @Query("supir_id") supirId: String
    ): Response<ApiResponse<List<Nothing>>>

    @FormUrlEncoded
    @POST("supir/driver/{id}/confirm")
    suspend fun doConfirmAngkot(
        @Path("id") id: String,
        @Field("is_confirmed") isConfirmed: Int
    ): Response<ApiResponse<Nothing>>

    @GET("ownersupir/chart/totalPendapatan")
    suspend fun getTotalEarnings(
        @Query("angkot_id") angkotId: String,
        @Query("supir_id") supirId: String
    ): Response<ApiResponse<TotalEarningsData>>

    @GET("ownersupir/chart/pendapatanHariIni")
    suspend fun getTodayEarning(
        @Query("angkot_id") angkotId: String,
        @Query("supir_id") supirId: String
    ): Response<ApiResponse<Int>>
}
