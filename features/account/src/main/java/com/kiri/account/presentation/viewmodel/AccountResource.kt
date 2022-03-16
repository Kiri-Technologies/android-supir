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

    // UPDATE
    fun onUpdateProfileLoading() {}
    fun onUpdateProfileSuccess(data: ProfileData?) {}
    fun onUpdateProfileFailed(error: String?) {}

    // PHOTO
    fun onUploadPhotoLoading() {}
    fun onUploadPhotoSuccess(data: ProfileData?) {}
    fun onUploadPhotoFailed(error: String?) {}

    // PASSWORD
    fun onUpdatePasswordLoading() {}
    fun onUpdatePasswordSuccess(data: ProfileData?) {}
    fun onUpdatePasswordFailed(error: String?) {}
}
