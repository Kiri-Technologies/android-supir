package com.kiri.trip.data

import com.kiri.common.utils.Resource
import com.kiri.trip.data.models.TripHistoryData
import kotlinx.coroutines.flow.Flow

interface TripRepository {
    suspend fun getTripHistory(sopirId: String): Flow<Resource<List<TripHistoryData>>>
}
