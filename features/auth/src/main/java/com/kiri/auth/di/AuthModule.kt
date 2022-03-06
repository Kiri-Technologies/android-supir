package com.kiri.auth.di

import androidx.lifecycle.Lifecycle
import com.kiri.auth.data.RepositoryImpl
import com.kiri.auth.data.Repository
import com.kiri.auth.data.endpoint.AuthEndpoint
import com.kiri.auth.data.endpoint.RemoteDataSource
import com.kiri.auth.domain.usecase.UseCaseImpl
import com.kiri.auth.domain.usecase.UseCase
import com.kiri.auth.presentation.viewmodel.AuthObserver
import com.kiri.auth.presentation.viewmodel.AuthResource
import com.kiri.auth.presentation.viewmodel.AuthViewModel
import com.kiri.common.utils.register
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val authApi = module {
    single(createdAtStart = false) { get<Retrofit>().create(AuthEndpoint::class.java) }

    single { RemoteDataSource(get()) }
    single<Repository> { RepositoryImpl(get()) }
    single<UseCase> { UseCaseImpl(RepositoryImpl(get())) }

    viewModel { (lifecycle: Lifecycle, resource: AuthResource) ->
        lifecycle.register(AuthViewModel(get())) { AuthObserver(resource, it) }
    }
}
