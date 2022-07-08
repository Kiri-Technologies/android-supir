package com.kiri.trip.di

import androidx.lifecycle.Lifecycle
import com.kiri.common.utils.register
import com.kiri.trip.data.AngkotRepository
import com.kiri.trip.data.AngkotRepositoryImpl
import com.kiri.trip.data.endpoint.AngkotEndpoint
import com.kiri.trip.data.endpoint.RemoteDataSource
import com.kiri.trip.domain.usecase.AngkotUseCase
import com.kiri.trip.domain.usecase.AngkotUseCaseImpl
import com.kiri.trip.presentation.viewmodel.AngkotObserver
import com.kiri.trip.presentation.viewmodel.AngkotResource
import com.kiri.trip.presentation.viewmodel.AngkotViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val tripModule = module {
    single(createdAtStart = false) { get<Retrofit>().create(AngkotEndpoint::class.java) }

    single { RemoteDataSource(get()) }
    single<AngkotRepository> { AngkotRepositoryImpl(get()) }
    single<AngkotUseCase> { AngkotUseCaseImpl(AngkotRepositoryImpl(get())) }

    viewModel { (lifecycle: Lifecycle, resource: AngkotResource) ->
        lifecycle.register(AngkotViewModel(get())) { AngkotObserver(resource, it) }
    }
}
