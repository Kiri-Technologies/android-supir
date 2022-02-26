package com.kiri.android.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kiri.android.R
import com.kiri.common.domain.PrefUseCaseImpl
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity(R.layout.activity_splash) {

    private val pref: PrefUseCaseImpl by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (pref.firstStart) {
            startActivity(Intent(this@SplashActivity, OnBoardingActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this@SplashActivity, AuthActivity::class.java))
            finish()
        }
    }
}
