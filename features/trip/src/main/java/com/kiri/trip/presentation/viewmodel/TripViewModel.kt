package com.kiri.trip.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiri.common.utils.Resource
import com.kiri.trip.domain.usecase.TripUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TripViewModel(private val useCase: TripUseCase) : ViewModel() {

    private val _history: MutableLiveData<Resource<Nothing>> = MutableLiveData()
    val history: LiveData<Resource<Nothing>> = _history

    fun getTripHistory(orderId: String) {
        viewModelScope.launch {
            _history.value = Resource.loading()
            useCase.getTripHistory(orderId).collect {
                _history.value = it
            }
        }
    }
}
