package com.kiri.account.domain.usecase

import com.kiri.account.data.AccountRepositoryImpl
import com.kiri.account.data.models.FeedbackAppData
import com.kiri.account.data.models.ProfileData
import com.kiri.account.data.models.UpdateProfileBody
import com.kiri.account.domain.usecase.model.ProfDom
import com.kiri.common.utils.ApiResponse
import com.kiri.common.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import okhttp3.MultipartBody

class AccountUseCaseImpl(private val accountRepositoryImpl: AccountRepositoryImpl) :
    AccountUseCase {
    override fun doLogout(): Flow<Resource<Nothing>> {
        return accountRepositoryImpl.doLogout()
    }

    override fun getProfile(): Flow<Resource<ProfileData>> {
        return flow {
            //
        }
    }

    override fun profile(): Flow<Resource<ProfDom>> {
        return accountRepositoryImpl.getProfile().map {
            val data = it.data?.dataData?.let { it1 ->
                ProfDom(
                    it1
                )
            }
            val dataClean = ApiResponse(data, it.error, it.status.name)
            Resource(it.status, dataClean, it.error)
        }
    }

    override fun doUpdate(body: UpdateProfileBody): Flow<Resource<ProfileData>> {
        return accountRepositoryImpl.doUpdate(body)
    }

    override fun doUploadPhoto(image: MultipartBody.Part): Flow<Resource<ProfileData>> {
        return accountRepositoryImpl.doUploadPhoto(image)
    }

    override fun doUpdatePassword(password: String): Flow<Resource<ProfileData>> {
        return accountRepositoryImpl.doUpdatePassword(password)
    }

    override fun feedbackApp(
        userId: String,
        review: String,
        comment: String
    ): Flow<Resource<FeedbackAppData>> {
        return accountRepositoryImpl.feedbackApp(userId, review, comment)
    }
}
