package com.kiri.trip.presentation.viewmodel

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.kiri.common.utils.Resource

class AngkotObserver(
    private val resource: AngkotResource,
    private val vm: AngkotViewModel
) : DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)

        vm.history.observe(owner) {
            when (it.status) {
                Resource.Status.LOADING -> resource.onTripHistoryLoading()
                Resource.Status.SUCCESS -> resource.onTripHistorySuccess(it.data)
                Resource.Status.ERROR -> resource.onTripHistoryFailed(it.error)
            }
        }

        vm.tripAngkot.observe(owner) {
            when (it.status) {
                Resource.Status.LOADING -> resource.onTripAngkotHistoryLoading()
                Resource.Status.SUCCESS -> resource.onTripAngkotHistorySuccess(it.data)
                Resource.Status.ERROR -> resource.onTripAngkotHistoryFailed(it.error)
            }
        }

        vm.feedback.observe(owner) {
            when (it.status) {
                Resource.Status.LOADING -> resource.onGetFeedbackLoading()
                Resource.Status.SUCCESS -> resource.onGetFeedbackSuccess(it.data)
                Resource.Status.ERROR -> resource.onGetFeedbackFailed(it.error)
            }
        }

        vm.angkot.observe(owner) {
            when (it.status) {
                Resource.Status.LOADING -> resource.onGetAngkotLoading()
                Resource.Status.SUCCESS -> resource.onGetAngkotSuccess(it.data)
                Resource.Status.ERROR -> resource.onGetAngkotFailed(it.error)
            }
        }

        vm.rideHistory.observe(owner) {
            when (it.status) {
                Resource.Status.LOADING -> resource.onRideHistoryLoading()
                Resource.Status.SUCCESS -> resource.onRideHistorySuccess(it.data)
                Resource.Status.ERROR -> resource.onRideHistoryFailed(it.error)
            }
        }

        vm.listAngkotConfirm.observe(owner) {
            when (it.status) {
                Resource.Status.LOADING -> resource.onListAngkotConfirmLoading()
                Resource.Status.SUCCESS -> resource.onListAngkotConfirmSuccess(it.data)
                Resource.Status.ERROR -> resource.onListAngkotConfirmFailed(it.error)
            }
        }

        vm.confirmAngkot.observe(owner) {
            when (it.status) {
                Resource.Status.LOADING -> resource.onConfirmAngkotLoading()
                Resource.Status.SUCCESS -> resource.onConfirmAngkotSuccess(it.data)
                Resource.Status.ERROR -> resource.onConfirmAngkotFailed(it.error)
            }
        }
        vm.totalEarnings.observe(owner) {
            when (it.status) {
                Resource.Status.LOADING -> resource.onTotalEarningsLoading()
                Resource.Status.SUCCESS -> resource.onTotalEarningsSuccess(it.data)
                Resource.Status.ERROR -> resource.onTotalEarningsFailed(it.error)
            }
        }
        vm.todayEarnings.observe(owner) {
            when (it.status) {
                Resource.Status.LOADING -> resource.onTodayEarningsLoading()
                Resource.Status.SUCCESS -> resource.onTodayEarningsSuccess(it.data)
                Resource.Status.ERROR -> resource.onTodayEarningsFailed(it.error)
            }
        }
        vm.avgUser.observe(owner) {
            when (it.status) {
                Resource.Status.LOADING -> resource.onAvgUserLoading()
                Resource.Status.SUCCESS -> resource.onAvgUserSuccess(it.data)
                Resource.Status.ERROR -> resource.onAvgUserFailed(it.error)
            }
        }
        vm.todayUser.observe(owner) {
            when (it.status) {
                Resource.Status.LOADING -> resource.onUserTodayLoading()
                Resource.Status.SUCCESS -> resource.onAUserTodaySuccess(it.data)
                Resource.Status.ERROR -> resource.onUserTodayFailed(it.error)
            }
        }
        vm.earningsToday.observe(owner) {
            when (it.status) {
                Resource.Status.LOADING -> resource.onEarningsTodayLoading()
                Resource.Status.SUCCESS -> resource.onEarningsTodaySuccess(it.data)
                Resource.Status.ERROR -> resource.onEarningsTodayFailed(it.error)
            }
        }
        vm.createEarning.observe(owner) {
            when (it.status) {
                Resource.Status.LOADING -> resource.onCreateEarningLoading()
                Resource.Status.SUCCESS -> resource.onCreateEarningSuccess(it.data)
                Resource.Status.ERROR -> resource.onCreateEarningFailed(it.error)
            }
        }
        vm.routes.observe(owner) {
            when (it.status) {
                Resource.Status.LOADING -> resource.onGetRoutesLoading()
                Resource.Status.SUCCESS -> resource.onGetRoutesSuccess(it.data)
                Resource.Status.ERROR -> resource.onGetRoutesFailed(it.error)
            }
        }
        vm.rideAngkot.observe(owner) {
            when (it.status) {
                Resource.Status.LOADING -> resource.onReadyRideLoading()
                Resource.Status.SUCCESS -> resource.onReadyRideSuccess(it.data)
                Resource.Status.ERROR -> resource.onReadyRideFailed(it.error)
            }
        }
        vm.setLocation.observe(owner) {
            when (it.status) {
                Resource.Status.LOADING -> resource.onSetLocationLoading()
                Resource.Status.SUCCESS -> resource.onSetLocationSuccess(it.data)
                Resource.Status.ERROR -> resource.onSetLocationFailed(it.error)
            }
        }
        vm.toggleStop.observe(owner) {
            when (it.status) {
                Resource.Status.LOADING -> resource.onToggleNgetemLoading()
                Resource.Status.SUCCESS -> resource.onToggleNgetemSuccess(it.data)
                Resource.Status.ERROR -> resource.onToggleNgetemFailed(it.error)
            }
        }
        vm.toggleFull.observe(owner) {
            when (it.status) {
                Resource.Status.LOADING -> resource.onToggleFullLoading()
                Resource.Status.SUCCESS -> resource.onToggleFullSuccess(it.data)
                Resource.Status.ERROR -> resource.onToggleFullFailed(it.error)
            }
        }
        vm.finishRide.observe(owner) {
            when (it.status) {
                Resource.Status.LOADING -> resource.onFinishRideLoading()
                Resource.Status.SUCCESS -> resource.onFinishRideSuccess(it.data)
                Resource.Status.ERROR -> resource.onFinishRideFailed(it.error)
            }
        }
    }
}
