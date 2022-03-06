package com.kiri.account.presentation.viewmodel

import com.kiri.account.data.models.ProfileData

interface AccountResource {

    // LOGOUT
    fun onLogoutLoading() {}
    fun onLogoutSuccess() {}
    fun onLogoutFailed(error: String?) {}

    // PROFILE
    fun onProfileLoading() {}
    fun onProfileSuccess(data: ProfileData?) {}
    fun onProfileFailed(error: String?) {}
}
