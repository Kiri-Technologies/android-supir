package com.kiri.trip.data

import com.kiri.common.utils.BaseApiResponse
import com.kiri.common.utils.Resource
import com.kiri.trip.data.endpoint.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TripRepositoryImpl(private val remoteDataSource: RemoteDataSource) : TripRepository,
    BaseApiResponse() {
    override suspend fun getTripHistory(sopirId: String): Flow<Resource<Nothing>> {
        return flow {
            emit(safeApiCall { remoteDataSource.getTripHistory(sopirId) })
        }
    }
}
