package com.kiri.trip.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiri.common.utils.Resource
import com.kiri.trip.data.models.AngkotData
import com.kiri.trip.data.models.FeedbackData
import com.kiri.trip.data.models.TripHistoryData
import com.kiri.trip.domain.usecase.AngkotUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AngkotViewModel(private val useCase: AngkotUseCase) : ViewModel() {

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

    private val _tripAngkot: MutableLiveData<Resource<List<TripHistoryData>>> = MutableLiveData()
    val tripAngkot: LiveData<Resource<List<TripHistoryData>>> = _tripAngkot

    fun getTripByAngkot(angkotId: String) {
        viewModelScope.launch {
            _tripAngkot.value = Resource.loading()
            useCase.getTripByAngkot(angkotId).collect {
                _tripAngkot.value = it
            }
        }
    }

    private val _feedback: MutableLiveData<Resource<FeedbackData>> = MutableLiveData()
    val feedback: LiveData<Resource<FeedbackData>> = _feedback

    fun getFeedbackByTrip(tripId: String) {
        viewModelScope.launch {
            _feedback.value = Resource.loading()
            useCase.getFeedbackByTripId(tripId).collect {
                _feedback.value = it
            }
        }
    }

    private val _angkot: MutableLiveData<Resource<List<AngkotData>>> = MutableLiveData()
    val angkot: LiveData<Resource<List<AngkotData>>> = _angkot

    fun getAngkot(supirId: String) {
        viewModelScope.launch {
            _angkot.value = Resource.loading()
            useCase.getAngkot(supirId).collect {
                _angkot.value = it
            }
        }
    }
}
