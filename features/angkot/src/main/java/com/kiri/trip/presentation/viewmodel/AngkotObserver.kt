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
    }
}
