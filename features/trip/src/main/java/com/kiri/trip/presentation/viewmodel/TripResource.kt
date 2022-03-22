package com.kiri.trip.presentation.viewmodel

interface TripResource {

    // Trip History
    fun onTripHistoryLoading() {}
    fun onTripHistorySuccess() {}
    fun onTripHistoryFailed(error: String?) {}
}
