package com.kiri.android.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.kiri.android.R
import com.kiri.common.domain.PrefUseCaseImpl
import org.koin.android.ext.android.inject

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity(R.layout.activity_splash) {

    private val pref: PrefUseCaseImpl by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                when {
                    pref.firstStart -> {
                        startActivity(Intent(this@SplashActivity, OnBoardingActivity::class.java))
                        finish()
                    }
                    !pref.token.isNullOrEmpty() -> {
                        startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
                        finish()
                    }
                    else -> {
                        startActivity(Intent(this@SplashActivity, AuthActivity::class.java))
                        finish()
                    }
                }
            },
            1000
        )
    }
}
