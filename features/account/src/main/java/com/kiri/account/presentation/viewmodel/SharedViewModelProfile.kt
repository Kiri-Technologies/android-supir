package com.kiri.account.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kiri.account.data.models.ProfileData

class SharedViewModelProfile : ViewModel() {

    private var _profileData: MutableLiveData<ProfileData> = MutableLiveData()
    private val profileData: LiveData<ProfileData> = _profileData

    fun setData(data: ProfileData) {
        _profileData.postValue(data)
    }

    fun getData() = profileData
}
