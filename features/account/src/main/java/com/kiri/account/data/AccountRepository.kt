package com.kiri.account.data

import com.kiri.account.data.models.FeedbackAppData
import com.kiri.account.data.models.ProfileData
import com.kiri.account.data.models.UpdateProfileBody
import com.kiri.common.utils.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface AccountRepository {

    fun doLogout(): Flow<Resource<Nothing>>
    fun getProfile(): Flow<Resource<ProfileData>>
    fun doUpdate(body: UpdateProfileBody): Flow<Resource<ProfileData>>
    fun doUploadPhoto(image: MultipartBody.Part): Flow<Resource<ProfileData>>
    fun doUpdatePassword(password: String): Flow<Resource<ProfileData>>
    fun feedbackApp(
        userId: String,
        review: String,
        comment: String,
    ): Flow<Resource<FeedbackAppData>>
}
