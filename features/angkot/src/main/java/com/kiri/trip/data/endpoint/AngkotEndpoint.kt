package com.kiri.trip.data.endpoint

import com.kiri.common.utils.ApiResponse
import com.kiri.trip.data.models.AngkotConfirmData
import com.kiri.trip.data.models.CreateHistoryData
import com.kiri.trip.data.models.EarningsByTodayData
import com.kiri.trip.data.models.FeedbackData
import com.kiri.trip.data.models.LocationBody
import com.kiri.trip.data.models.PremiumData
import com.kiri.trip.data.models.RiwayatNarikData
import com.kiri.trip.data.models.RoutesData
import com.kiri.trip.data.models.SetWayBody
import com.kiri.trip.data.models.ToggleFullBody
import com.kiri.trip.data.models.ToggleStopBody
import com.kiri.trip.data.models.TotalEarningsData
import com.kiri.trip.data.models.TripHistoryData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

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

    @GET("ownersupir/chart/averagePenumpangPernarik")
    suspend fun getAvgUser(
        @Query("supir_id") supirId: String
    ): Response<ApiResponse<Int>>

    @GET("ownersupir/chart/totalPenumpangHariIni")
    suspend fun getUserToday(
        @Query("supir_id") supirId: String
    ): Response<ApiResponse<Int>>

    @GET("ownersupir/chart/pendapatanHarian")
    suspend fun getEarningsByToday(
        @Query("angkot_id") angkotId: String,
        @Query("supir_id") supirId: String
    ): Response<ApiResponse<EarningsByTodayData>>

    @FormUrlEncoded
    @POST("supir/riwayat/{id}/update")
    suspend fun createEarningNote(
        @Path("id") historyId: String,
        @Field("selesai_narik") finishRide: String? = null,
        @Field("jumlah_pendapatan") earnings: Int? = null
    ): Response<ApiResponse<Nothing>>

    @GET("routes/{id}")
    suspend fun getRoutesById(
        @Path("id") angkotId: String
    ): Response<ApiResponse<RoutesData>>

    @FormUrlEncoded
    @POST("supir/angkot/{id}/updateStatusOperasi")
    suspend fun statusAngkot(
        @Path("id") angkotId: String,
        @Field("is_beroperasi") is_Beroperasi: String,
        @Field("supir_yg_beroperasi") supirId: String? = null
    ): Response<ApiResponse<Nothing>>

    @FormUrlEncoded
    @POST("supir/riwayat/create")
    suspend fun createHistory(
        @Field("user_id") supirId: String,
        @Field("angkot_id") angkotId: String,
        @Field("mulai_narik") rideTime: String
    ): Response<ApiResponse<CreateHistoryData>>

    @POST
    suspend fun setWayMaps(@Url url: String, @Body body: SetWayBody): Response<ApiResponse<Nothing>>

    @POST
    suspend fun setLocation(
        @Url url: String,
        @Body body: LocationBody
    ): Response<ApiResponse<Nothing>>

    @POST
    suspend fun toggleStop(
        @Url url: String,
        @Body body: ToggleStopBody
    ): Response<ApiResponse<Nothing>>

    @POST
    suspend fun toggleFull(
        @Url url: String,
        @Body body: ToggleFullBody
    ): Response<ApiResponse<Nothing>>

    @FormUrlEncoded
    @POST("supir/premiumuser/check")
    suspend fun premium(
        @Field("user_id") supirId: String,
    ): Response<ApiResponse<PremiumData>>
}
