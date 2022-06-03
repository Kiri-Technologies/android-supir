package com.kiri.trip.data

import com.kiri.common.utils.Resource
import com.kiri.trip.data.models.AngkotConfirmData
import com.kiri.trip.data.models.EarningsByTodayData
import com.kiri.trip.data.models.FeedbackData
import com.kiri.trip.data.models.RiwayatNarikData
import com.kiri.trip.data.models.TotalEarningsData
import com.kiri.trip.data.models.TripHistoryData
import kotlinx.coroutines.flow.Flow

interface AngkotRepository {
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
    ): Flow<Resource<TotalEarningsData>>

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

    suspend fun getEarningsByToday(
        angkotId: String,
        supirId: String
    ): Flow<Resource<EarningsByTodayData>>

    suspend fun createEarningNote(
        earningId: String,
        earnings: Int
    ): Flow<Resource<Nothing>>
}
