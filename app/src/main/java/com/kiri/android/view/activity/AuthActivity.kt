package com.kiri.android.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.kiri.android.R

class AuthActivity : AppCompatActivity(R.layout.activity_auth) {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        navController = navHostFragment.navController
        val graph = navController.navInflater.inflate(R.navigation.auth_nav)
        navController.graph = graph
    }

    override fun onBackPressed() {
        if (navController.currentDestination?.id == R.id.loginFragment) {
            finish()
        } else {
            super.onBackPressed()
        }
    }
}
