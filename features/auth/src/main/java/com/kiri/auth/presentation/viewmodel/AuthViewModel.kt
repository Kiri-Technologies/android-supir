package com.kiri.auth.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiri.auth.data.models.LoginData
import com.kiri.auth.data.models.RegisterBody
import com.kiri.auth.data.models.RegisterData
import com.kiri.auth.domain.usecase.UseCaseImpl
import com.kiri.common.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AuthViewModel(private val useCase: UseCaseImpl) : ViewModel(){

    private val _login: MutableLiveData<Resource<LoginData?>> = MutableLiveData()
    var login: LiveData<Resource<LoginData?>> = _login

    fun login(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            _login.value = Resource.loading()
            useCase.login(email, password)
                .collect { value ->
                    _login.value = value
                }
        }
    }

    private val _register: MutableLiveData<Resource<RegisterData?>> = MutableLiveData()
    var register: LiveData<Resource<RegisterData?>> = _register

    fun register(
        body: RegisterBody
    ) {
        viewModelScope.launch {
            _register.value = Resource.loading()
            useCase.register(body)
                .collect { value ->
                    _register.value = value
                }
        }
    }
}
