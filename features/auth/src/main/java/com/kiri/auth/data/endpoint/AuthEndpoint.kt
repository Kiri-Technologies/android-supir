package com.kiri.auth.data.endpoint

import com.kiri.auth.data.models.LoginData
import com.kiri.auth.data.models.RegisterBody
import com.kiri.auth.data.models.RegisterData
import com.kiri.common.utils.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthEndpoint {

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<ApiResponse<LoginData>>

    @POST("register")
    suspend fun register(
        @Body registerBody: RegisterBody
    ): Response<ApiResponse<RegisterData>>
}
