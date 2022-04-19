package com.kiri.trip.domain.usecase

import com.kiri.common.utils.Resource
import com.kiri.trip.data.AngkotRepositoryImpl
import com.kiri.trip.data.models.AngkotConfirmData
import com.kiri.trip.data.models.AngkotData
import com.kiri.trip.data.models.FeedbackData
import com.kiri.trip.data.models.RiwayatNarikData
import com.kiri.trip.data.models.TripHistoryData
import kotlinx.coroutines.flow.Flow

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
}