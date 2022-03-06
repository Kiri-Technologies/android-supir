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
                    resource.onProfileSuccess(it.data)
                }
                Resource.Status.ERROR -> {
                    resource.onProfileFailed(it.error)
                }
            }
        }
    }
}
