package com.kiri.trip.presentation.viewmodel

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.kiri.common.utils.Resource

class TripObserver(
    private val resource: TripResource,
    private val vm: TripViewModel
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
    }
}
