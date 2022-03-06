package com.kiri.common.di

import com.kiri.common.data.pref.PrefKey
import com.kiri.common.data.pref.PrefRepositoryImpl
import com.kiri.common.data.pref.PrefRepository
import com.kiri.common.data.pref.SharedPref
import com.kiri.common.domain.PrefUseCaseImpl
import com.kiri.common.domain.PrefUseCase
import org.koin.dsl.module

val prefModule = module {
    single {
        SharedPref(
            get(),
            name = PrefKey.PREFNAME,
        )
    }
    single<PrefRepository> {
        PrefRepositoryImpl(get())
    }
    single<PrefUseCase> {
        PrefUseCaseImpl(PrefRepositoryImpl(get()))
    }
}
