package com.kiri.android.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kiri.android.R
import com.kiri.android.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity(R.layout.activity_splash) {

    private val binding by viewBinding(ActivitySplashBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(Intent(this@SplashActivity, OnBoardingActivity::class.java))
        finish()
    }
}
