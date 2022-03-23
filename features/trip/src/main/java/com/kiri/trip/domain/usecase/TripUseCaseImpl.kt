package com.kiri.trip.domain.usecase

import com.kiri.common.utils.Resource
import com.kiri.trip.data.TripRepositoryImpl
import com.kiri.trip.data.models.TripHistoryData
import kotlinx.coroutines.flow.Flow

class TripUseCaseImpl(private val tripRepositoryImpl: TripRepositoryImpl) : TripUseCase {
    override suspend fun getTripHistory(sopirId: String): Flow<Resource<List<TripHistoryData>>> {
        return tripRepositoryImpl.getTripHistory(sopirId)
    }
}
