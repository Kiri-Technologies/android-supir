package com.kiri.trip.data

import com.kiri.common.utils.Resource
import com.kiri.trip.data.models.AngkotData
import com.kiri.trip.data.models.FeedbackData
import com.kiri.trip.data.models.TripHistoryData
import kotlinx.coroutines.flow.Flow

interface AngkotRepository {
    suspend fun getTripHistory(sopirId: String): Flow<Resource<List<TripHistoryData>>>
    suspend fun getTripByAngkot(angkotId: String): Flow<Resource<List<TripHistoryData>>>
    suspend fun getFeedbackByTripId(tripId: String): Flow<Resource<FeedbackData>>
    suspend fun getAngkot(supirId: String): Flow<Resource<List<AngkotData>>>
}
