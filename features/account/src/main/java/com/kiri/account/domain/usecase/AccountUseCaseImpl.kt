package com.kiri.account.domain.usecase

import com.kiri.account.data.AccountRepositoryImpl
import com.kiri.account.data.models.ProfileData
import com.kiri.account.data.models.UpdateProfileBody
import com.kiri.common.utils.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.InputStream

class AccountUseCaseImpl(private val accountRepositoryImpl: AccountRepositoryImpl) :
    AccountUseCase {
    override fun doLogout(): Flow<Resource<Nothing>> {
        return accountRepositoryImpl.doLogout()
    }

    override fun getProfile(): Flow<Resource<ProfileData>> {
        return accountRepositoryImpl.getProfile()
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
}
