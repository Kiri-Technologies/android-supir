package com.kiri.android.view.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kiri.account.data.models.ProfileData
import com.kiri.account.presentation.viewmodel.AccountResource
import com.kiri.account.presentation.viewmodel.AccountViewModel
import com.kiri.account.presentation.viewmodel.SharedViewModelProfile
import com.kiri.android.R
import com.kiri.android.databinding.ActivityHomeBinding
import com.kiri.common.data.pref.PrefKey
import com.kiri.common.domain.PrefUseCase
import com.kiri.common.utils.ConnectionCheck
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HomeActivity : AppCompatActivity(), AccountResource {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController
    private val viewModel: AccountViewModel by viewModel {
        parametersOf(lifecycle, this)
    }
    private val sharedVM by viewModels<SharedViewModelProfile>()
    private val pref: PrefUseCase by inject()
    private lateinit var connectionCheck: ConnectionCheck

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        setSupportActionBar(binding.toolbar)

        navController = findNavController(R.id.nav_host_fragment_activity_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications,
                R.id.navigation_account
            )
        )
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        connectionCheck()
    }

    override fun onBackPressed() {
        if (navController.currentDestination?.id == R.id.navigation_home)
            finish()
        else
            super.onBackPressed()
    }

    override fun onProfileLoading() {
        super.onProfileLoading()
    }

    override fun onProfileSuccess(data: ProfileData?) {
        super.onProfileSuccess(data)
        if (data != null) {
            sharedVM.setData(data)
        }
    }

    override fun onProfileFailed(error: String?) {
        super.onProfileFailed(error)
        if (error == getString(R.string.error_401)) {
            pref.removeByKey(PrefKey.TOKEN)
            finish()
        }
    }

    private fun connectionCheck() {
        connectionCheck = ConnectionCheck(application)
        connectionCheck.observe(this) { isConnected ->
            if (isConnected) {
                viewModel.getProfile()
            }
        }
    }
}
