package com.kiri.auth.presentation.viewmodel

import com.kiri.auth.domain.models.LoginDomain

interface AuthResource {

    // Login
    fun onLoginLoading() {}
    fun onLoginSuccess(data: LoginDomain?) {}
    fun onLoginFailed(error: Throwable?) {}
}
