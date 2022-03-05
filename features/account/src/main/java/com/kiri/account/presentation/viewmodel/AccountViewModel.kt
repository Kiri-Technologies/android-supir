package com.kiri.account.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiri.account.domain.usecase.AccountUseCase
import com.kiri.common.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AccountViewModel(private val useCase: AccountUseCase) : ViewModel() {

    private var _logout: MutableLiveData<Resource<Nothing>> = MutableLiveData()
    val logout: LiveData<Resource<Nothing>> = _logout

    fun doLogout() = viewModelScope.launch {
        _logout.value = Resource.loading()
        useCase.doLogout().collect {
            _logout.value = it
        }
    }
}
