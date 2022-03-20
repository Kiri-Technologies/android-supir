package com.kiri.account.data

import com.kiri.account.data.endpoint.RemoteDataSource
import com.kiri.account.data.models.FeedbackAppData
import com.kiri.account.data.models.ProfileData
import com.kiri.account.data.models.UpdateProfileBody
import com.kiri.common.utils.BaseApiResponse
import com.kiri.common.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody

class AccountRepositoryImpl(private val remoteDataSource: RemoteDataSource) :
    AccountRepository,
    BaseApiResponse() {
    override fun doLogout(): Flow<Resource<Nothing>> {
        return flow {
            emit(safeApiCall { remoteDataSource.doLogout() })
        }
    }

    override fun getProfile(): Flow<Resource<ProfileData>> {
        return flow {
            emit(safeApiCall { remoteDataSource.getProfile() })
        }
    }

    override fun doUpdate(body: UpdateProfileBody): Flow<Resource<ProfileData>> {
        return flow {
            emit(safeApiCall { remoteDataSource.doUpdate(body) })
        }
    }

    override fun doUploadPhoto(image: MultipartBody.Part): Flow<Resource<ProfileData>> {
        return flow {
            emit(safeApiCall { remoteDataSource.doUploadPhoto(image) })
        }
    }

    override fun doUpdatePassword(password: String): Flow<Resource<ProfileData>> {
        return flow {
            emit(safeApiCall { remoteDataSource.doUpdatePassword(password) })
        }
    }

    override fun feedbackApp(
        userId: String,
        review: String,
        comment: String
    ): Flow<Resource<FeedbackAppData>> {
        return flow {
            emit(safeApiCall { remoteDataSource.feedbackApp(userId, review, comment) })
        }
    }
}
