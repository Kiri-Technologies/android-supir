package com.kiri.account.presentation.viewmodel

interface AccountResource {

    // LOGOUT

    fun onLogoutLoading()
    fun onLogoutSuccess()
    fun onLogoutFailed(error: String?)
}
