package com.kiri.account.domain.usecase

import com.kiri.account.data.AccountRepositoryImpl
import com.kiri.account.data.models.ProfileData
import com.kiri.common.utils.Resource
import kotlinx.coroutines.flow.Flow

class AccountUseCaseImpl(private val accountRepositoryImpl: AccountRepositoryImpl) :
    AccountUseCase {
    override fun doLogout(): Flow<Resource<Nothing>> {
        return accountRepositoryImpl.doLogout()
    }

    override fun getProfile(): Flow<Resource<ProfileData>> {
        return accountRepositoryImpl.getProfile()
    }
}
