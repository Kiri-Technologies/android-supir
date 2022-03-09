package com.kiri.android.view.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kiri.android.R
import com.kiri.android.databinding.ActivityAccountBinding

class AccountActivity : AppCompatActivity(R.layout.activity_account) {
    private val binding by viewBinding<ActivityAccountBinding>()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        navController = navHostFragment.navController
        binding.toolbar.setupWithNavController(navController)
        val graph = navController.navInflater.inflate(R.navigation.account_nav)
        navController.graph = graph
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}
