package com.kiri.trip.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiri.common.utils.Resource
import com.kiri.trip.data.models.TripHistoryData
import com.kiri.trip.domain.usecase.TripUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TripViewModel(private val useCase: TripUseCase) : ViewModel() {

    private val _history: MutableLiveData<Resource<List<TripHistoryData>>> = MutableLiveData()
    val history: LiveData<Resource<List<TripHistoryData>>> = _history

    fun getTripHistory(sopirId: String) {
        viewModelScope.launch {
            _history.value = Resource.loading()
            useCase.getTripHistory(sopirId).collect {
                _history.value = it
            }
        }
    }
}
