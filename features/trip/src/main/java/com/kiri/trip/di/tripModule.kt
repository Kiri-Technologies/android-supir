package com.kiri.trip.di

import androidx.lifecycle.Lifecycle
import com.kiri.common.utils.register
import com.kiri.trip.data.TripRepository
import com.kiri.trip.data.TripRepositoryImpl
import com.kiri.trip.data.endpoint.RemoteDataSource
import com.kiri.trip.data.endpoint.TripEndpoint
import com.kiri.trip.domain.usecase.TripUseCase
import com.kiri.trip.domain.usecase.TripUseCaseImpl
import com.kiri.trip.presentation.viewmodel.TripObserver
import com.kiri.trip.presentation.viewmodel.TripResource
import com.kiri.trip.presentation.viewmodel.TripViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val tripModule = module {
    single(createdAtStart = false) { get<Retrofit>().create(TripEndpoint::class.java) }

    single { RemoteDataSource(get()) }
    single<TripRepository> { TripRepositoryImpl(get()) }
    single<TripUseCase> { TripUseCaseImpl(TripRepositoryImpl(get())) }

    viewModel { (lifecycle: Lifecycle, resource: TripResource) ->
        lifecycle.register(TripViewModel(get())) { TripObserver(resource, it) }
    }
}
