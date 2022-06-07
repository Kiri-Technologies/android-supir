package com.kiri.android.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kiri.android.R
import com.kiri.android.databinding.ActivityAccountBinding
import com.kiri.ui.gone

class RideAngkotActivity : AppCompatActivity(R.layout.activity_account) {
    private val binding by viewBinding<ActivityAccountBinding>()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.toolbar.gone()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        navController = navHostFragment.navController
        val graph = navController.navInflater.inflate(R.navigation.ride_angkot_navigation)
        navController.graph = graph
    }

    override fun onBackPressed() {
        //
    }
}
