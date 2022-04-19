package com.kiri.account.domain.usecase

import com.kiri.account.data.AccountRepository
import com.kiri.account.domain.usecase.model.ProfDom
import com.kiri.common.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AccountUseCase : AccountRepository {
    fun profile(): Flow<Resource<ProfDom>>
}
