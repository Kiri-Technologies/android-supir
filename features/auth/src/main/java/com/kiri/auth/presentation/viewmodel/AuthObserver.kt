package com.kiri.auth.presentation.viewmodel

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.kiri.common.utils.Resource

class AuthObserver(
    private val resource: AuthResource,
    private val viewModel: AuthViewModel
) : DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)

        viewModel.login.observe(owner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    resource.onLoginLoading()
                }
                Resource.Status.SUCCESS -> {
                    resource.onLoginSuccess(it.data)
                }
                Resource.Status.ERROR -> {
                    resource.onLoginFailed(it.error)
                }
            }
        }

        viewModel.register.observe(owner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    resource.onRegisterLoading()
                }
                Resource.Status.SUCCESS -> {
                    resource.onRegisterSuccess(it.data)
                }
                Resource.Status.ERROR -> {
                    resource.onRegisterFailed(it.error)
                }
            }
        }
    }
}
