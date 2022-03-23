package com.kiri.trip.data.endpoint

import com.kiri.common.utils.ApiResponse
import com.kiri.trip.data.models.TripHistoryData
import retrofit2.Response

class RemoteDataSource(private val endpoint: TripEndpoint) : TripEndpoint {
    override suspend fun getTripHistory(sopirId: String): Response<ApiResponse<List<TripHistoryData>>> {
        return endpoint.getTripHistory(sopirId)
    }
}
