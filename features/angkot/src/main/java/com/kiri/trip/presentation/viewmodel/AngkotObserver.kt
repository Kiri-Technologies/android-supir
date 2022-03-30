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
    }
}
