package com.kiri.account.domain.usecase

import com.kiri.account.data.AccountRepositoryImpl
import com.kiri.common.utils.Resource
import kotlinx.coroutines.flow.Flow

class AccountUseCaseImpl(private val accountRepositoryImpl: AccountRepositoryImpl) :
    AccountUseCase {
    override fun doLogout(): Flow<Resource<Nothing>> {
        return accountRepositoryImpl.doLogout()
    }
}
