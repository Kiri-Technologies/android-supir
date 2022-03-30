package com.kiri.trip.domain.usecase

import com.kiri.common.utils.Resource
import com.kiri.trip.data.AngkotRepositoryImpl
import com.kiri.trip.data.models.AngkotData
import com.kiri.trip.data.models.FeedbackData
import com.kiri.trip.data.models.TripHistoryData
import kotlinx.coroutines.flow.Flow

class AngkotUseCaseImpl(private val angkotRepositoryImpl: AngkotRepositoryImpl) : AngkotUseCase {
    override suspend fun getTripHistory(sopirId: String): Flow<Resource<List<TripHistoryData>>> {
        return angkotRepositoryImpl.getTripHistory(sopirId)
    }

    override suspend fun getTripByAngkot(angkotId: String): Flow<Resource<List<TripHistoryData>>> {
        return angkotRepositoryImpl.getTripByAngkot(angkotId)
    }

    override suspend fun getFeedbackByTripId(tripId: String): Flow<Resource<FeedbackData>> {
        return angkotRepositoryImpl.getFeedbackByTripId(tripId)
    }

    override suspend fun getAngkot(supirId: String): Flow<Resource<List<AngkotData>>> {
       return angkotRepositoryImpl.getAngkot(supirId)
    }
}
