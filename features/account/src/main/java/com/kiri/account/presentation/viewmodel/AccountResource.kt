package com.kiri.account.presentation.viewmodel

import com.kiri.account.data.models.FeedbackAppData
import com.kiri.account.data.models.ProfileData
import com.kiri.account.domain.usecase.model.ProfDom

interface AccountResource {

    // LOGOUT
    fun onLogoutLoading() {}
    fun onLogoutSuccess() {}
    fun onLogoutFailed(error: String?) {}

    // PROFILE
    fun onProfileLoading() {}
    fun onProfileSuccess(data: ProfDom?) {}
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

    // FEEDBACK_APP
    fun onFeedbackAppLoading() {}
    fun onFeedbackAppSuccess(data: FeedbackAppData?) {}
    fun onFeedbackAppFailed(error: String?) {}
}
