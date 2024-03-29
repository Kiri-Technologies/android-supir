package com.kiri.trip.domain.usecase

import androidx.lifecycle.MutableLiveData
import com.kiri.common.utils.Resource
import com.kiri.common.utils.ResourceFb
import com.kiri.trip.data.models.AngkotConfirmData
import com.kiri.trip.data.models.AngkotDistanceData
import com.kiri.trip.data.models.CreateHistoryData
import com.kiri.trip.data.models.EarningsByTodayData
import com.kiri.trip.data.models.FeedbackData
import com.kiri.trip.data.models.LocationBody
import com.kiri.trip.data.models.PremiumData
import com.kiri.trip.data.models.RiwayatNarikData
import com.kiri.trip.data.models.RoutesData
import com.kiri.trip.data.models.SetWayBody
import com.kiri.trip.data.models.ToggleFullBody
import com.kiri.trip.data.models.ToggleStopBody
import com.kiri.trip.data.models.TripHistoryData
import com.kiri.trip.data.models.UserAngkot
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
        historyId: String,
        finishRide: String?,
        earnings: Int?
    ): Flow<Resource<Nothing>>

    suspend fun getRoutesById(angkotId: String): Flow<Resource<RoutesData>>

    fun getAngkotDistance(angkotId: String): MutableLiveData<ResourceFb<AngkotDistanceData>>

    suspend fun statusAngkot(
        angkotId: String,
        is_Beroperasi: String,
        supirId: String
    ): Flow<Resource<Nothing>>

    suspend fun createHistory(
        supirId: String,
        angkotId: String,
        rideTime: String
    ): Flow<Resource<CreateHistoryData>>

    suspend fun setWayMaps(body: SetWayBody): Flow<Resource<Nothing>>

    suspend fun send3API(
        angkotId: String,
        is_Beroperasi: String,
        supirId: String,
        rideTime: String,
        body: SetWayBody
    ): Flow<Resource<CreateHistoryData>>

    suspend fun setLocation(
        body: LocationBody
    ): Flow<Resource<Nothing>>

    suspend fun toggleStop(
        body: ToggleStopBody
    ): Flow<Resource<Nothing>>

    suspend fun toggleFull(
        body: ToggleFullBody
    ): Flow<Resource<Nothing>>

    fun getUserAngkotRide(angkotId: String): MutableLiveData<ResourceFb<MutableList<UserAngkot>>>
    fun getUserAngkotDrop(angkotId: String): MutableLiveData<ResourceFb<MutableList<UserAngkot>>>

    suspend fun finishRide(
        angkotId: String,
        is_Beroperasi: String,
        supirId: String?,
        historyId: String,
        finishRide: String?,
        earnings: Int?,
        body: SetWayBody
    ): Flow<Resource<Nothing>>

    suspend fun premium(supirId: String): Flow<Resource<PremiumData>>
}
