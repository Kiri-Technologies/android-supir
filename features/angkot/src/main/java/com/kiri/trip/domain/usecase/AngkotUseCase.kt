package com.kiri.trip.domain.usecase

import com.kiri.common.utils.Resource
import com.kiri.trip.data.models.AngkotConfirmData
import com.kiri.trip.data.models.FeedbackData
import com.kiri.trip.data.models.RiwayatNarikData
import com.kiri.trip.data.models.TripHistoryData
import com.kiri.trip.domain.usecase.models.TotalEarningsDomain
import kotlinx.coroutines.flow.Flow

interface AngkotUseCase {

    suspend fun getTripHistory(sopirId: String): Flow<Resource<List<TripHistoryData>>>
    suspend fun getTripByAngkot(
        is_done: Int,
        angkotId: String,
        supirId: String
    ): Flow<Resource<List<TripHistoryData>>>

    suspend fun getFeedbackByTripId(tripId: String): Flow<Resource<FeedbackData>>
    suspend fun getAngkot(supirId: String): Flow<Resource<List<AngkotConfirmData>>>
    suspend fun getRideHistory(
        angkotId: String,
        supirId: String
    ): Flow<Resource<List<RiwayatNarikData>>>

    suspend fun getListAngkotConfirmation(
        supirId: String
    ): Flow<Resource<List<Nothing>>>

    suspend fun doConfirmAngkot(
        id: String,
        isConfirmed: Int
    ): Flow<Resource<Nothing>>

    suspend fun getTotalEarnings(
        angkotId: String,
        supirId: String
    ): Flow<Resource<TotalEarningsDomain>>

    suspend fun getTodayEarning(
        angkotId: String,
        supirId: String
    ): Flow<Resource<Int>>

    suspend fun getAvgUser(
        supirId: String
    ): Flow<Resource<Int>>

    suspend fun getUserToday(
        supirId: String
    ): Flow<Resource<Int>>
}
