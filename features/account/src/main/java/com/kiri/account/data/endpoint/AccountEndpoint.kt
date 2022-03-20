package com.kiri.account.data.endpoint

import com.kiri.account.data.models.FeedbackAppData
import com.kiri.account.data.models.ProfileData
import com.kiri.account.data.models.UpdateProfileBody
import com.kiri.common.utils.ApiResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AccountEndpoint {

    @GET("logout")
    suspend fun doLogout(): Response<ApiResponse<Nothing>>

    @GET("profile")
    suspend fun getProfile(): Response<ApiResponse<ProfileData>>

    @POST("profile/update")
    suspend fun doUpdate(
        @Body body: UpdateProfileBody
    ): Response<ApiResponse<ProfileData>>

    @Multipart
    @POST("profile/update/image")
    suspend fun doUploadPhoto(
        @Part image: MultipartBody.Part
    ): Response<ApiResponse<ProfileData>>

    @FormUrlEncoded
    @POST("profile/update/password")
    suspend fun doUpdatePassword(
        @Field("password") password: String
    ): Response<ApiResponse<ProfileData>>

    @FormUrlEncoded
    @POST("feedbackapp/create")
    suspend fun feedbackApp(
        @Field("user_id") userId: String,
        @Field("review") review: String,
        @Field("tanggapan") comment: String,
    ): Response<ApiResponse<FeedbackAppData>>
}
