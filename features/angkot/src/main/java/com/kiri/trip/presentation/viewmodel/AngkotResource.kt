package com.kiri.trip.presentation.viewmodel

import com.kiri.common.utils.ApiResponse
import com.kiri.trip.data.models.AngkotConfirmData
import com.kiri.trip.data.models.FeedbackData
import com.kiri.trip.data.models.RiwayatNarikData
import com.kiri.trip.data.models.TripHistoryData
import com.kiri.trip.domain.usecase.models.TotalEarningsDomain

interface AngkotResource {

    // Trip History
    fun onTripHistoryLoading() {}
    fun onTripHistorySuccess(data: ApiResponse<List<TripHistoryData>>?) {}
    fun onTripHistoryFailed(error: String?) {}

    // Trip Angkot
    fun onTripAngkotHistoryLoading() {}
    fun onTripAngkotHistorySuccess(data: ApiResponse<List<TripHistoryData>>?) {}
    fun onTripAngkotHistoryFailed(error: String?) {}

    // Feedback
    fun onGetFeedbackLoading() {}
    fun onGetFeedbackSuccess(data: ApiResponse<FeedbackData>?) {}
    fun onGetFeedbackFailed(error: String?) {}

    // Angkot
    fun onGetAngkotLoading() {}
    fun onGetAngkotSuccess(data: ApiResponse<List<AngkotConfirmData>>?) {}
    fun onGetAngkotFailed(error: String?) {}

    // RideHistory
    fun onRideHistoryLoading() {}
    fun onRideHistorySuccess(data: ApiResponse<List<RiwayatNarikData>>?) {}
    fun onRideHistoryFailed(error: String?) {}

    // ListAngkotConfirm
    fun onListAngkotConfirmLoading() {}
    fun onListAngkotConfirmSuccess(data: ApiResponse<List<Nothing>>?) {}
    fun onListAngkotConfirmFailed(error: String?) {}

    // ConfirmAngkot
    fun onConfirmAngkotLoading() {}
    fun onConfirmAngkotSuccess(data: ApiResponse<List<Nothing>>?) {}
    fun onConfirmAngkotFailed(error: String?) {}

    // TotalEarnings
    fun onTotalEarningsLoading() {}
    fun onTotalEarningsSuccess(data: ApiResponse<TotalEarningsDomain>?) {}
    fun onTotalEarningsFailed(error: String?) {}

    // TodayEarnings
    fun onTodayEarningsLoading() {}
    fun onTodayEarningsSuccess(data: ApiResponse<Int>?) {}
    fun onTodayEarningsFailed(error: String?) {}

    // AvgUser
    fun onAvgUserLoading() {}
    fun onAvgUserSuccess(data: ApiResponse<Int>?) {}
    fun onAvgUserFailed(error: String?) {}

    // UserToday
    fun onUserTodayLoading() {}
    fun onAUserTodaySuccess(data: ApiResponse<Int>?) {}
    fun onUserTodayFailed(error: String?) {}
}
