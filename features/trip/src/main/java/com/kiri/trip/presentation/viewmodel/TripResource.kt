package com.kiri.trip.presentation.viewmodel

import com.kiri.common.utils.ApiResponse
import com.kiri.trip.data.models.TripHistoryData

interface TripResource {

    // Trip History
    fun onTripHistoryLoading() {}
    fun onTripHistorySuccess(data: ApiResponse<List<TripHistoryData>>?) {}
    fun onTripHistoryFailed(error: String?) {}
}
