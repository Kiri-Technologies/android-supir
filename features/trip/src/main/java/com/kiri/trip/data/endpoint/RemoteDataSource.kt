package com.kiri.trip.data.endpoint

import com.kiri.common.utils.ApiResponse
import retrofit2.Response

class RemoteDataSource(private val endpoint: TripEndpoint): TripEndpoint {
    override suspend fun getTripHistory(sopirId: String): Response<ApiResponse<Nothing>> {
        return endpoint.getTripHistory(sopirId)
    }
}