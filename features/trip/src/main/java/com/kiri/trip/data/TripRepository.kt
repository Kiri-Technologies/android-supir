package com.kiri.trip.data

import com.kiri.common.utils.Resource
import kotlinx.coroutines.flow.Flow

interface TripRepository {
    suspend fun getTripHistory(sopirId: String): Flow<Resource<Nothing>>
}
