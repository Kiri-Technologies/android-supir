package com.kiri.android

import android.app.Application
import com.kiri.account.di.accountModule
import com.kiri.android.view.viewmodel.WidgetViewModel
import com.kiri.auth.di.authApi
import com.kiri.common.di.RetrofitModule
import com.kiri.common.di.prefModule
import com.kiri.trip.di.tripModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class MainApplication : Application() {
    companion object {
        lateinit var instance: MainApplication
            private set
    }

    val app = module {
        single {
            WidgetViewModel()
        }
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        startKoin {
//            androidLogger(Level.DEBUG)
            androidContext(this@MainApplication)
            modules(
                listOf(
                    RetrofitModule,
                    prefModule,
                    accountModule,
                    authApi,
                    tripModule,
                    app
                )
            )
        }
        instance = this
    }
}
