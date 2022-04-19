package com.kiri.account.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiri.account.data.models.FeedbackAppData
import com.kiri.account.data.models.ProfileData
import com.kiri.account.data.models.UpdateProfileBody
import com.kiri.account.domain.usecase.AccountUseCase
import com.kiri.account.domain.usecase.model.ProfDom
import com.kiri.common.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class AccountViewModel(private val useCase: AccountUseCase) : ViewModel() {

    private var _logout: MutableLiveData<Resource<Nothing>> = MutableLiveData()
    val logout: LiveData<Resource<Nothing>> = _logout

    fun doLogout() = viewModelScope.launch {
        _logout.value = Resource.loading()
        useCase.doLogout().collect {
            _logout.value = it
        }
    }

    private var _profile: MutableLiveData<Resource<ProfDom>> = MutableLiveData()
    val profile: LiveData<Resource<ProfDom>> = _profile

    fun getProfile() = viewModelScope.launch {
        _profile.value = Resource.loading()
        useCase.profile().collect {
            _profile.value = it
        }
    }

    private var _update: MutableLiveData<Resource<ProfileData>> = MutableLiveData()
    val update: LiveData<Resource<ProfileData>> = _update

    fun doUpdate(body: UpdateProfileBody) = viewModelScope.launch {
        _update.value = Resource.loading()
        useCase.doUpdate(body).collect {
            _update.value = it
        }
    }

    private var _uploadPhoto: MutableLiveData<Resource<ProfileData>> = MutableLiveData()
    val uploadPhoto: LiveData<Resource<ProfileData>> = _uploadPhoto

    fun doUploadPhoto(image: MultipartBody.Part) = viewModelScope.launch {
        _uploadPhoto.value = Resource.loading()
        useCase.doUploadPhoto(image).collect {
            _uploadPhoto.value = it
        }
    }

    private var _updatePassword: MutableLiveData<Resource<ProfileData>> = MutableLiveData()
    val updatePassword: LiveData<Resource<ProfileData>> = _updatePassword

    fun doUpdatePassword(password: String) = viewModelScope.launch {
        _updatePassword.value = Resource.loading()
        useCase.doUpdatePassword(password).collect {
            _updatePassword.value = it
        }
    }

    private var _feedbackApp: MutableLiveData<Resource<FeedbackAppData>> = MutableLiveData()
    val feedbackApp: LiveData<Resource<FeedbackAppData>> = _feedbackApp

    fun feedbackApp(
        userId: String,
        review: String,
        comment: String
    ) = viewModelScope.launch {
        _feedbackApp.value = Resource.loading()
        useCase.feedbackApp(userId, review, comment).collect {
            _feedbackApp.value = it
        }
    }
}
