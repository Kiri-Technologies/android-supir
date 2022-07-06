package com.kiri.trip.domain.usecase

import androidx.lifecycle.MutableLiveData
import com.kiri.common.utils.ApiResponse
import com.kiri.common.utils.Resource
import com.kiri.trip.data.AngkotRepositoryImpl
import com.kiri.trip.data.models.AngkotConfirmData
import com.kiri.trip.data.models.AngkotData
import com.kiri.trip.data.models.EarningsByTodayData
import com.kiri.trip.data.models.FeedbackData
import com.kiri.trip.data.models.LocationBody
import com.kiri.trip.data.models.RiwayatNarikData
import com.kiri.trip.data.models.RoutesData
import com.kiri.trip.data.models.ToggleFullBody
import com.kiri.trip.data.models.ToggleStopBody
import com.kiri.trip.data.models.TripHistoryData
import com.kiri.trip.data.models.SetWayBody
import com.kiri.trip.domain.usecase.models.TotalEarningsDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map

class AngkotUseCaseImpl(private val angkotRepositoryImpl: AngkotRepositoryImpl) : AngkotUseCase {
    override suspend fun getTripHistory(sopirId: String): Flow<Resource<List<TripHistoryData>>> {
        return angkotRepositoryImpl.getTripHistory(sopirId)
    }

    override suspend fun getTripByAngkot(
        is_done: Int,
        angkotId: String,
        supirId: String
    ): Flow<Resource<List<TripHistoryData>>> {
        return angkotRepositoryImpl.getTripByAngkot(is_done, angkotId, supirId)
    }

    override suspend fun getFeedbackByTripId(tripId: String): Flow<Resource<FeedbackData>> {
        return angkotRepositoryImpl.getFeedbackByTripId(tripId)
    }

    override suspend fun getAngkot(supirId: String): Flow<Resource<List<AngkotConfirmData>>> {
        return angkotRepositoryImpl.getAngkot(supirId)
    }

    override suspend fun getRideHistory(
        angkotId: String,
        supirId: String
    ): Flow<Resource<List<RiwayatNarikData>>> {
        return angkotRepositoryImpl.getRideHistory(angkotId, supirId)
    }

    override suspend fun getListAngkotConfirmation(supirId: String): Flow<Resource<List<Nothing>>> {
        return angkotRepositoryImpl.getListAngkotConfirmation(supirId)
    }

    override suspend fun doConfirmAngkot(
        id: String,
        isConfirmed: Int
    ): Flow<Resource<Nothing>> {
        return angkotRepositoryImpl.doConfirmAngkot(id, isConfirmed)
    }

    override suspend fun getTotalEarnings(
        angkotId: String,
        supirId: String
    ): Flow<Resource<TotalEarningsDomain>> {
        return angkotRepositoryImpl.getTotalEarnings(angkotId, supirId).map {
            val data = it.data?.dataData?.let { totalData ->
                TotalEarningsDomain(totalData)
            }
            val dataClean = ApiResponse(data, it.data?.message, it.error)
            Resource(it.status, dataClean, it.error)
        }
    }

    override suspend fun getTodayEarning(angkotId: String, supirId: String): Flow<Resource<Int>> {
        return angkotRepositoryImpl.getTodayEarning(angkotId, supirId)
    }

    override suspend fun getAvgUser(supirId: String): Flow<Resource<Int>> {
        return angkotRepositoryImpl.getAvgUser(supirId)
    }

    override suspend fun getUserToday(supirId: String): Flow<Resource<Int>> {
        return angkotRepositoryImpl.getUserToday(supirId)
    }

    override suspend fun getEarningsByToday(
        angkotId: String,
        supirId: String
    ): Flow<Resource<EarningsByTodayData>> {
        return angkotRepositoryImpl.getEarningsByToday(angkotId, supirId)
    }

    override suspend fun createEarningNote(
        earningId: String,
        earnings: Int
    ): Flow<Resource<Nothing>> {
        return angkotRepositoryImpl.createEarningNote(earningId, earnings)
    }

    override suspend fun getRoutesById(angkotId: String): Flow<Resource<RoutesData>> {
        return angkotRepositoryImpl.getRoutesById(angkotId)
    }

    override suspend fun getAngkotDistance(): MutableLiveData<Resource<AngkotData>> {
        return angkotRepositoryImpl.getAngkotDistance()
    }

    override suspend fun statusAngkot(
        angkotId: String,
        is_Beroperasi: String,
        supirId: String
    ): Flow<Resource<Nothing>> {
        return angkotRepositoryImpl.statusAngkot(angkotId, is_Beroperasi, supirId)
    }

    override suspend fun createHistory(
        supirId: String,
        angkotId: String,
        rideTime: String
    ): Flow<Resource<Nothing>> {
        return angkotRepositoryImpl.createHistory(supirId, angkotId, rideTime)
    }

    override suspend fun setWayMaps(body: SetWayBody): Flow<Resource<Nothing>> {
        return angkotRepositoryImpl.setWayMaps(body)
    }

    override suspend fun send3API(
        angkotId: String,
        is_Beroperasi: String,
        supirId: String,
        rideTime: String,
        body: SetWayBody
    ): Flow<Resource<Nothing>> {
        return angkotRepositoryImpl.statusAngkot(angkotId, is_Beroperasi, supirId).flatMapConcat {
            angkotRepositoryImpl.createHistory(supirId, angkotId, rideTime)
        }.flatMapConcat {
            angkotRepositoryImpl.setWayMaps(body)
        }
    }

    override suspend fun setLocation(body: LocationBody): Flow<Resource<Nothing>> {
        return angkotRepositoryImpl.setLocation(body)
    }

    override suspend fun toggleStop(body: ToggleStopBody): Flow<Resource<Nothing>> {
        return angkotRepositoryImpl.toggleStop(body)
    }

    override suspend fun toggleFull(body: ToggleFullBody): Flow<Resource<Nothing>> {
        return angkotRepositoryImpl.toggleFull(body)
    }
}
