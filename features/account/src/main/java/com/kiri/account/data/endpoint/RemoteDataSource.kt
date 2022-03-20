package com.kiri.account.data.endpoint

import com.kiri.account.data.models.FeedbackAppData
import com.kiri.account.data.models.ProfileData
import com.kiri.account.data.models.UpdateProfileBody
import com.kiri.common.utils.ApiResponse
import okhttp3.MultipartBody
import retrofit2.Response

class RemoteDataSource(private val accountEndpoint: AccountEndpoint) : AccountEndpoint {
    override suspend fun doLogout(): Response<ApiResponse<Nothing>> {
        return accountEndpoint.doLogout()
    }

    override suspend fun getProfile(): Response<ApiResponse<ProfileData>> {
        return accountEndpoint.getProfile()
    }

    override suspend fun doUpdate(body: UpdateProfileBody): Response<ApiResponse<ProfileData>> {
        return accountEndpoint.doUpdate(body)
    }

    override suspend fun doUploadPhoto(image: MultipartBody.Part): Response<ApiResponse<ProfileData>> {
        return accountEndpoint.doUploadPhoto(image)
    }

    override suspend fun doUpdatePassword(password: String): Response<ApiResponse<ProfileData>> {
        return accountEndpoint.doUpdatePassword(password)
    }

    override suspend fun feedbackApp(
        userId: String,
        review: String,
        comment: String
    ): Response<ApiResponse<FeedbackAppData>> {
        return accountEndpoint.feedbackApp(userId, review, comment)
    }
}
