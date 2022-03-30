package com.kiri.trip.data.endpoint

import com.kiri.common.utils.ApiResponse
import com.kiri.trip.data.models.AngkotData
import com.kiri.trip.data.models.FeedbackData
import com.kiri.trip.data.models.TripHistoryData
import retrofit2.Response

class RemoteDataSource(private val endpoint: AngkotEndpoint) : AngkotEndpoint {
    override suspend fun getTripHistory(sopirId: String): Response<ApiResponse<List<TripHistoryData>>> {
        return endpoint.getTripHistory(sopirId)
    }

    override suspend fun getTripByAngkot(angkotId: String): Response<ApiResponse<List<TripHistoryData>>> {
        return endpoint.getTripByAngkot(angkotId)
    }

    override suspend fun getFeedbackByTripId(tripId: String): Response<ApiResponse<FeedbackData>> {
        return endpoint.getFeedbackByTripId(tripId)
    }

    override suspend fun getAngkot(supirId: String): Response<ApiResponse<List<AngkotData>>> {
        return endpoint.getAngkot(supirId)
    }
}
