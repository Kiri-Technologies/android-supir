package com.kiri.account.presentation.viewmodel

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.kiri.common.utils.Resource

class AccountObserver(
    private val viewModel: AccountViewModel,
    private val resource: AccountResource
) : DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)

        viewModel.logout.observe(owner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    resource.onLogoutLoading()
                }
                Resource.Status.SUCCESS -> {
                    resource.onLogoutSuccess()
                }
                Resource.Status.ERROR -> {
                    resource.onLogoutFailed(it.error)
                }
            }
        }

        viewModel.profile.observe(owner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    resource.onProfileLoading()
                }
                Resource.Status.SUCCESS -> {
                    resource.onProfileSuccess(it.data?.dataData)
                }
                Resource.Status.ERROR -> {
                    resource.onProfileFailed(it.error)
                }
            }
        }

        viewModel.update.observe(owner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    resource.onUpdateProfileLoading()
                }
                Resource.Status.SUCCESS -> {
                    resource.onUpdateProfileSuccess(it.data?.dataData)
                }
                Resource.Status.ERROR -> {
                    resource.onUpdateProfileFailed(it.error)
                }
            }
        }

        viewModel.uploadPhoto.observe(owner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    resource.onUploadPhotoLoading()
                }
                Resource.Status.SUCCESS -> {
                    resource.onUploadPhotoSuccess(it.data?.dataData)
                }
                Resource.Status.ERROR -> {
                    resource.onUploadPhotoFailed(it.error)
                }
            }
        }

        viewModel.updatePassword.observe(owner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    resource.onUpdatePasswordLoading()
                }
                Resource.Status.SUCCESS -> {
                    resource.onUpdatePasswordSuccess(it.data?.dataData)
                }
                Resource.Status.ERROR -> {
                    resource.onUpdatePasswordFailed(it.error)
                }
            }
        }

        viewModel.feedbackApp.observe(owner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    resource.onFeedbackAppLoading()
                }
                Resource.Status.SUCCESS -> {
                    resource.onFeedbackAppSuccess(it.data?.dataData)
                }
                Resource.Status.ERROR -> {
                    resource.onFeedbackAppFailed(it.error)
                }
            }
        }
    }
}
