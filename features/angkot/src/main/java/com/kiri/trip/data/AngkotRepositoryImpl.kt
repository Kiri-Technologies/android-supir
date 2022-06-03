package com.kiri.trip.data

import com.kiri.common.utils.BaseApiResponse
import com.kiri.common.utils.Resource
import com.kiri.trip.data.endpoint.RemoteDataSource
import com.kiri.trip.data.models.AngkotConfirmData
import com.kiri.trip.data.models.EarningsByTodayData
import com.kiri.trip.data.models.FeedbackData
import com.kiri.trip.data.models.RiwayatNarikData
import com.kiri.trip.data.models.TotalEarningsData
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

    override suspend fun getTripByAngkot(
        is_done: Int,
        angkotId: String,
        supirId: String
    ): Flow<Resource<List<TripHistoryData>>> {
        return flow {
            emit(safeApiCall { remoteDataSource.getTripByAngkot(is_done, angkotId, supirId) })
        }
    }

    override suspend fun getFeedbackByTripId(tripId: String): Flow<Resource<FeedbackData>> {
        return flow {
            emit(safeApiCall { remoteDataSource.getFeedbackByTripId(tripId) })
        }
    }

    override suspend fun getAngkot(supirId: String): Flow<Resource<List<AngkotConfirmData>>> {
        return flow {
            emit(safeApiCall { remoteDataSource.getAngkot(supirId) })
        }
    }

    override suspend fun getRideHistory(
        angkotId: String,
        supirId: String
    ): Flow<Resource<List<RiwayatNarikData>>> {
        return flow {
            emit(safeApiCall { remoteDataSource.getRideHistory(angkotId, supirId) })
        }
    }

    override suspend fun getListAngkotConfirmation(supirId: String): Flow<Resource<List<Nothing>>> {
        return flow {
            emit(safeApiCall { remoteDataSource.getListAngkotConfirmation(supirId) })
        }
    }

    override suspend fun doConfirmAngkot(
        id: String,
        isConfirmed: Int
    ): Flow<Resource<Nothing>> {
        return flow {
            emit(
                safeApiCall {
                    remoteDataSource.doConfirmAngkot(
                        id,
                        isConfirmed
                    )
                }
            )
        }
    }

    override suspend fun getTotalEarnings(
        angkotId: String,
        supirId: String
    ): Flow<Resource<TotalEarningsData>> {
        return flow { emit(safeApiCall { remoteDataSource.getTotalEarnings(angkotId, supirId) }) }
    }

    override suspend fun getTodayEarning(
        angkotId: String,
        supirId: String
    ): Flow<Resource<Int>> {
        return flow { emit(safeApiCall { remoteDataSource.getTodayEarning(angkotId, supirId) }) }
    }

    override suspend fun getAvgUser(supirId: String): Flow<Resource<Int>> {
        return flow { emit(safeApiCall { remoteDataSource.getAvgUser(supirId) }) }
    }

    override suspend fun getUserToday(supirId: String): Flow<Resource<Int>> {
        return flow { emit(safeApiCall { remoteDataSource.getUserToday(supirId) }) }
    }

    override suspend fun getEarningsByToday(
        angkotId: String,
        supirId: String
    ): Flow<Resource<EarningsByTodayData>> {
        return flow { emit(safeApiCall { remoteDataSource.getEarningsByToday(angkotId, supirId) }) }
    }

    override suspend fun createEarningNote(
        earningId: String,
        earnings: Int
    ): Flow<Resource<Nothing>> {
        return flow {
            emit(
                safeApiCall {
                    remoteDataSource.createEarningNote(
                        earningId,
                        earnings
                    )
                }
            )
        }
    }
}
