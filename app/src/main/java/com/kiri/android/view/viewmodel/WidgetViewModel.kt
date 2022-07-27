package com.kiri.android.view.viewmodel

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WidgetViewModel : ViewModel() {

    private var currentLocation: MutableLiveData<Location> = MutableLiveData()
    fun getCurrentLocation(): LiveData<Location> = currentLocation
    fun setCurrentLocation(location: Location) = currentLocation.postValue(location)
}
