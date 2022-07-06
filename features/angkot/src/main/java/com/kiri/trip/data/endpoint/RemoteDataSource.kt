package com.kiri.trip.data.endpoint

import com.kiri.common.utils.ApiResponse
import com.kiri.trip.data.models.AngkotConfirmData
import com.kiri.trip.data.models.EarningsByTodayData
import com.kiri.trip.data.models.FeedbackData
import com.kiri.trip.data.models.LocationBody
import com.kiri.trip.data.models.RiwayatNarikData
import com.kiri.trip.data.models.RoutesData
import com.kiri.trip.data.models.ToggleFullBody
import com.kiri.trip.data.models.ToggleStopBody
import com.kiri.trip.data.models.TotalEarningsData
import com.kiri.trip.data.models.TripHistoryData
import com.kiri.trip.data.models.SetWayBody
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

    override suspend fun getTotalEarnings(
        angkotId: String,
        supirId: String
    ): Response<ApiResponse<TotalEarningsData>> {
        return endpoint.getTotalEarnings(angkotId, supirId)
    }

    override suspend fun getTodayEarning(
        angkotId: String,
        supirId: String
    ): Response<ApiResponse<Int>> {
        return endpoint.getTodayEarning(angkotId, supirId)
    }

    override suspend fun getAvgUser(supirId: String): Response<ApiResponse<Int>> {
        return endpoint.getAvgUser(supirId)
    }

    override suspend fun getUserToday(supirId: String): Response<ApiResponse<Int>> {
        return endpoint.getUserToday(supirId)
    }

    override suspend fun getEarningsByToday(
        angkotId: String,
        supirId: String
    ): Response<ApiResponse<EarningsByTodayData>> {
        return endpoint.getEarningsByToday(angkotId, supirId)
    }

    override suspend fun createEarningNote(
        earningId: String,
        earnings: Int
    ): Response<ApiResponse<Nothing>> {
        return endpoint.createEarningNote(earningId, earnings)
    }

    override suspend fun getRoutesById(angkotId: String): Response<ApiResponse<RoutesData>> {
        return endpoint.getRoutesById(angkotId)
    }

    override suspend fun statusAngkot(
        angkotId: String,
        is_Beroperasi: String,
        supirId: String
    ): Response<ApiResponse<Nothing>> {
        return endpoint.statusAngkot(angkotId, is_Beroperasi, supirId)
    }

    override suspend fun createHistory(
        supirId: String,
        angkotId: String,
        rideTime: String
    ): Response<ApiResponse<Nothing>> {
        return endpoint.createHistory(supirId, angkotId, rideTime)
    }

    override suspend fun setWayMaps(url: String, body: SetWayBody): Response<ApiResponse<Nothing>> {
        return endpoint.setWayMaps(url, body)
    }

    override suspend fun setLocation(
        url: String,
        body: LocationBody
    ): Response<ApiResponse<Nothing>> {
        return endpoint.setLocation(url, body)
    }

    override suspend fun toggleStop(
        url: String,
        body: ToggleStopBody
    ): Response<ApiResponse<Nothing>> {
        return endpoint.toggleStop(url, body)
    }

    override suspend fun toggleFull(
        url: String,
        body: ToggleFullBody
    ): Response<ApiResponse<Nothing>> {
        return endpoint.toggleFull(url, body)
    }
}
