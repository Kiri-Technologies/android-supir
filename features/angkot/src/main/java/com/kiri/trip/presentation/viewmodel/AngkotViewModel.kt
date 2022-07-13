package com.kiri.trip.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiri.common.utils.Resource
import com.kiri.common.utils.ResourceFb
import com.kiri.trip.data.models.AngkotConfirmData
import com.kiri.trip.data.models.AngkotDistanceData
import com.kiri.trip.data.models.EarningsByTodayData
import com.kiri.trip.data.models.FeedbackData
import com.kiri.trip.data.models.LocationBody
import com.kiri.trip.data.models.RiwayatNarikData
import com.kiri.trip.data.models.RoutesData
import com.kiri.trip.data.models.SetWayBody
import com.kiri.trip.data.models.ToggleFullBody
import com.kiri.trip.data.models.ToggleStopBody
import com.kiri.trip.data.models.TripHistoryData
import com.kiri.trip.data.models.UserAngkot
import com.kiri.trip.domain.usecase.AngkotUseCase
import com.kiri.trip.domain.usecase.models.TotalEarningsDomain
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

    fun getTripByAngkot(
        is_done: Int,
        angkotId: String,
        supirId: String
    ) {
        viewModelScope.launch {
            _tripAngkot.value = Resource.loading()
            useCase.getTripByAngkot(is_done, angkotId, supirId).collect {
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

    private val _angkot: MutableLiveData<Resource<List<AngkotConfirmData>>> = MutableLiveData()
    val angkot: LiveData<Resource<List<AngkotConfirmData>>> = _angkot

    fun getAngkot(supirId: String) {
        viewModelScope.launch {
            _angkot.value = Resource.loading()
            useCase.getAngkot(supirId).collect {
                _angkot.value = it
            }
        }
    }

    private val _rideHistory: MutableLiveData<Resource<List<RiwayatNarikData>>> = MutableLiveData()
    val rideHistory: LiveData<Resource<List<RiwayatNarikData>>> = _rideHistory

    fun getRideHistory(
        angkotId: String,
        supirId: String
    ) {
        viewModelScope.launch {
            _rideHistory.value = Resource.loading()
            useCase.getRideHistory(angkotId, supirId).collect {
                _rideHistory.value = it
            }
        }
    }

    private val _listAngkotConfirm: MutableLiveData<Resource<List<Nothing>>> = MutableLiveData()
    val listAngkotConfirm: LiveData<Resource<List<Nothing>>> = _listAngkotConfirm

    fun getListAngkotConfirmation(
        supirId: String
    ) {
        viewModelScope.launch {
            _listAngkotConfirm.value = Resource.loading()
            useCase.getListAngkotConfirmation(supirId).collect {
                _listAngkotConfirm.value = it
            }
        }
    }

    private val _confirmAngkot: MutableLiveData<Resource<List<Nothing>>> = MutableLiveData()
    val confirmAngkot: LiveData<Resource<List<Nothing>>> = _confirmAngkot

    fun doConfirmAngkot(
        id: String,
        isConfirmed: Int
    ) {
        viewModelScope.launch {
            _confirmAngkot.value = Resource.loading()
            useCase.doConfirmAngkot(id, isConfirmed).collect {
                _confirmAngkot.value = it
            }
        }
    }

    private val _totalEarnings: MutableLiveData<Resource<TotalEarningsDomain>> = MutableLiveData()
    val totalEarnings: LiveData<Resource<TotalEarningsDomain>> = _totalEarnings

    fun getTotalEarnings(
        angkotId: String,
        supirId: String
    ) {
        viewModelScope.launch {
            _totalEarnings.value = Resource.loading()
            useCase.getTotalEarnings(angkotId, supirId).collect {
                _totalEarnings.value = it
            }
        }
    }

    private val _todayEarnings: MutableLiveData<Resource<Int>> = MutableLiveData()
    val todayEarnings: LiveData<Resource<Int>> = _todayEarnings

    fun getTodayEarnings(
        angkotId: String,
        supirId: String
    ) {
        viewModelScope.launch {
            _todayEarnings.value = Resource.loading()
            useCase.getTodayEarning(angkotId, supirId).collect {
                _todayEarnings.value = it
            }
        }
    }

    private val _avgUser: MutableLiveData<Resource<Int>> = MutableLiveData()
    val avgUser: LiveData<Resource<Int>> = _avgUser

    fun getAvgUser(
        supirId: String
    ) {
        viewModelScope.launch {
            _avgUser.value = Resource.loading()
            useCase.getAvgUser(supirId).collect {
                _avgUser.value = it
            }
        }
    }

    private val _todayUser: MutableLiveData<Resource<Int>> = MutableLiveData()
    val todayUser: LiveData<Resource<Int>> = _todayUser

    fun getUserToday(
        supirId: String
    ) {
        viewModelScope.launch {
            _todayUser.value = Resource.loading()
            useCase.getUserToday(supirId).collect {
                _todayUser.value = it
            }
        }
    }

    private val _earningsToday: MutableLiveData<Resource<EarningsByTodayData>> = MutableLiveData()
    val earningsToday: LiveData<Resource<EarningsByTodayData>> = _earningsToday

    fun getEarningsToday(
        angkotId: String,
        supirId: String
    ) {
        viewModelScope.launch {
            _earningsToday.value = Resource.loading()
            useCase.getEarningsByToday(angkotId, supirId).collect {
                _earningsToday.value = it
            }
        }
    }

    private val _createEarning: MutableLiveData<Resource<Nothing>> = MutableLiveData()
    val createEarning: LiveData<Resource<Nothing>> = _createEarning

    fun createEarning(
        earningId: String,
        earning: Int
    ) {
        viewModelScope.launch {
            _createEarning.value = Resource.loading()
            useCase.createEarningNote(earningId, earning).collect {
                _createEarning.value = it
            }
        }
    }

    private val _routes: MutableLiveData<Resource<RoutesData>> = MutableLiveData()
    val routes: LiveData<Resource<RoutesData>> = _routes

    fun getRoutes(
        angkotId: String
    ) {
        viewModelScope.launch {
            _routes.value = Resource.loading()
            useCase.getRoutesById(angkotId).collect {
                _routes.value = it
            }
        }
    }

    private val _sendToRideAngkot: MutableLiveData<Resource<Nothing>> = MutableLiveData()
    val rideAngkot: LiveData<Resource<Nothing>> = _sendToRideAngkot

    fun rideAngkot(
        angkotId: String,
        is_Beroperasi: String,
        supirId: String,
        rideTime: String,
        body: SetWayBody
    ) {
        viewModelScope.launch {
            _sendToRideAngkot.value = Resource.loading()
            useCase.send3API(angkotId, is_Beroperasi, supirId, rideTime, body).collect {
                _sendToRideAngkot.value = it
            }
        }
    }

    private val _setLocation: MutableLiveData<Resource<Nothing>> = MutableLiveData()
    val setLocation: LiveData<Resource<Nothing>> = _setLocation

    fun setLocation(body: LocationBody) {
        viewModelScope.launch {
            _setLocation.value = Resource.loading()
            useCase.setLocation(body).collect {
                _setLocation.value = it
            }
        }
    }

    private val _toggleStop: MutableLiveData<Resource<Nothing>> = MutableLiveData()
    val toggleStop: LiveData<Resource<Nothing>> = _toggleStop

    fun toggleNgetem(body: ToggleStopBody) {
        viewModelScope.launch {
            _toggleStop.value = Resource.loading()
            useCase.toggleStop(body).collect {
                _toggleStop.value = it
            }
        }
    }

    private val _toggleFull: MutableLiveData<Resource<Nothing>> = MutableLiveData()
    val toggleFull: LiveData<Resource<Nothing>> = _toggleFull

    fun toggleFull(body: ToggleFullBody) {
        viewModelScope.launch {
            _toggleFull.value = Resource.loading()
            useCase.toggleFull(body).collect {
                _toggleFull.value = it
            }
        }
    }

    fun angkotDistance(angkotId: String): LiveData<ResourceFb<AngkotDistanceData>> =
        useCase.getAngkotDistance(angkotId)

    fun getUserAngkotRide(angkotId: String): LiveData<ResourceFb<MutableList<UserAngkot>>> =
        useCase.getUserAngkotRide(angkotId)

    fun getUserAngkotDrop(angkotId: String): LiveData<ResourceFb<MutableList<UserAngkot>>> =
        useCase.getUserAngkotDrop(angkotId)
}
