package com.kiri.account.data

import com.kiri.common.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    fun doLogout(): Flow<Resource<Nothing>>
}
