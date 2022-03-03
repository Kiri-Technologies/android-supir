package com.kiri.auth.presentation.viewmodel

import com.kiri.auth.data.models.LoginData
import com.kiri.auth.data.models.RegisterData

interface AuthResource {

    // Login
    fun onLoginLoading() {}
    fun onLoginSuccess(data: LoginData?) {}
    fun onLoginFailed(error: String?) {}

    // Register
    fun onRegisterLoading() {}
    fun onRegisterSuccess(data: RegisterData?) {}
    fun onRegisterFailed(error: String?) {}
}
