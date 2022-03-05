package com.kiri.account.di

import androidx.lifecycle.Lifecycle
import com.kiri.account.data.AccountRepository
import com.kiri.account.data.AccountRepositoryImpl
import com.kiri.account.data.endpoint.AccountEndpoint
import com.kiri.account.data.endpoint.RemoteDataSource
import com.kiri.account.domain.usecase.AccountUseCase
import com.kiri.account.domain.usecase.AccountUseCaseImpl
import com.kiri.account.presentation.viewmodel.AccountObserver
import com.kiri.account.presentation.viewmodel.AccountResource
import com.kiri.account.presentation.viewmodel.AccountViewModel
import com.kiri.common.utils.register
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val accountModule = module {
    single(createdAtStart = false) { get<Retrofit>().create(AccountEndpoint::class.java) }

    single { RemoteDataSource(get()) }
    single<AccountRepository> { AccountRepositoryImpl(get()) }
    single<AccountUseCase> { AccountUseCaseImpl(AccountRepositoryImpl(get())) }

    viewModel { (lifecycle: Lifecycle, resource: AccountResource) ->
        lifecycle.register(AccountViewModel(get())) { AccountObserver(it, resource) }
    }
}
