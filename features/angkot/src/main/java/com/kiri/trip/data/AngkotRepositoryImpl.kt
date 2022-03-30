package com.kiri.trip.data

import com.kiri.common.utils.BaseApiResponse
import com.kiri.common.utils.Resource
import com.kiri.trip.data.endpoint.RemoteDataSource
import com.kiri.trip.data.models.AngkotData
import com.kiri.trip.data.models.FeedbackData
import com.kiri.trip.data.models.TripHistoryData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AngkotRepositoryImpl(private val remoteDataSource: RemoteDataSource) : AngkotRepository,
    BaseApiResponse() {
    override suspend fun getTripHistory(sopirId: String): Flow<Resource<List<TripHistoryData>>> {
        return flow {
            emit(safeApiCall { remoteDataSource.getTripHistory(sopirId) })
        }
    }

    override suspend fun getTripByAngkot(angkotId: String): Flow<Resource<List<TripHistoryData>>> {
        return flow {
            emit(safeApiCall { remoteDataSource.getTripByAngkot(angkotId) })
        }
    }

    override suspend fun getFeedbackByTripId(tripId: String): Flow<Resource<FeedbackData>> {
        return flow {
            emit(safeApiCall { remoteDataSource.getFeedbackByTripId(tripId) })
        }
    }

    override suspend fun getAngkot(supirId: String): Flow<Resource<List<AngkotData>>> {
        return flow {
            emit(safeApiCall { remoteDataSource.getAngkot(supirId) })
        }
    }
}
