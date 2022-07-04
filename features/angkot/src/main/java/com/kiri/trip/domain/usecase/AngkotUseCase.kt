package com.kiri.trip.domain.usecase

import androidx.lifecycle.MutableLiveData
import com.kiri.common.utils.Resource
import com.kiri.trip.data.models.AngkotConfirmData
import com.kiri.trip.data.models.AngkotData
import com.kiri.trip.data.models.EarningsByTodayData
import com.kiri.trip.data.models.FeedbackData
import com.kiri.trip.data.models.RiwayatNarikData
import com.kiri.trip.data.models.RoutesData
import com.kiri.trip.data.models.TripHistoryData
import com.kiri.trip.data.models.setWayBody
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

    suspend fun getEarningsByToday(
        angkotId: String,
        supirId: String
    ): Flow<Resource<EarningsByTodayData>>

    suspend fun createEarningNote(
        earningId: String,
        earnings: Int
    ): Flow<Resource<Nothing>>

    suspend fun getRoutesById(angkotId: String): Flow<Resource<RoutesData>>

    suspend fun getAngkotDistance(): MutableLiveData<Resource<AngkotData>>

    suspend fun statusAngkot(
        angkotId: String,
        is_Beroperasi: String,
        supirId: String
    ): Flow<Resource<Nothing>>

    suspend fun createHistory(
        supirId: String,
        angkotId: String,
        rideTime: String
    ): Flow<Resource<Nothing>>

    suspend fun setWayMaps(body: setWayBody): Flow<Resource<Nothing>>

    suspend fun send3API(
        angkotId: String,
        is_Beroperasi: String,
        supirId: String,
        rideTime: String,
        body: setWayBody
    ): Flow<Resource<Nothing>>
}
