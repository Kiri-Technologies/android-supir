package com.kiri.trip.data.endpoint

import com.kiri.common.utils.ApiResponse
import com.kiri.trip.data.models.AngkotConfirmData
import com.kiri.trip.data.models.FeedbackData
import com.kiri.trip.data.models.RiwayatNarikData
import com.kiri.trip.data.models.TripHistoryData
import retrofit2.Response

class RemoteDataSource(private val endpoint: AngkotEndpoint) : AngkotEndpoint {
    override suspend fun getTripHistory(sopirId: String): Response<ApiResponse<List<TripHistoryData>>> {
        return endpoint.getTripHistory(sopirId)
    }

    override suspend fun getTripByAngkot(
        is_done: Int,
        angkotId: String,
        supirId: String
    ): Response<ApiResponse<List<TripHistoryData>>> {
        return endpoint.getTripByAngkot(is_done, angkotId, supirId)
    }

    override suspend fun getFeedbackByTripId(tripId: String): Response<ApiResponse<FeedbackData>> {
        return endpoint.getFeedbackByTripId(tripId)
    }

    override suspend fun getAngkot(supirId: String): Response<ApiResponse<List<AngkotConfirmData>>> {
        return endpoint.getAngkot(supirId)
    }

    override suspend fun getRideHistory(
        angkotId: String,
        supirId: String
    ): Response<ApiResponse<List<RiwayatNarikData>>> {
        return endpoint.getRideHistory(angkotId, supirId)
    }

    override suspend fun getListAngkotConfirmation(supirId: String): Response<ApiResponse<List<Nothing>>> {
        return endpoint.getListAngkotConfirmation(supirId)
    }

    override suspend fun doConfirmAngkot(
        id: String,
        isConfirmed: Int
    ): Response<ApiResponse<Nothing>> {
        return endpoint.doConfirmAngkot(id, isConfirmed)
    }
}