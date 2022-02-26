package com.kiri.auth.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiri.auth.domain.models.LoginDomain
import com.kiri.auth.domain.usecase.UseCase
import com.kiri.common.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class AuthViewModel(private val useCase: UseCase) : ViewModel() {

    private var _login: MutableLiveData<Resource<LoginDomain>> = MutableLiveData()
    val login: LiveData<Resource<LoginDomain>> = _login

    fun login(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            Resource.loading(null)
            useCase.login(email, password)
                .flowOn(Dispatchers.IO)
                .catch { Resource.error(it, null) }
                .collect { Resource.success(it) }
        }
    }
}
