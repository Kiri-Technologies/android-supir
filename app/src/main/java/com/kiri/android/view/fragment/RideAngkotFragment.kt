package com.kiri.android.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kiri.android.R
import com.kiri.android.data.calonUser
import com.kiri.android.databinding.FragmentRideAngkotBinding
import com.kiri.android.view.adapter.DropUserAdapter
import com.kiri.android.view.adapter.UserRideAdapter
import com.kiri.common.domain.PrefUseCase
import org.koin.android.ext.android.inject

class RideAngkotFragment : Fragment(R.layout.fragment_ride_angkot), View.OnClickListener {
    private val binding by viewBinding<FragmentRideAngkotBinding>()
    private val pref by inject<PrefUseCase>()
    private val dropAdapter by lazy {
        DropUserAdapter()
    }
    private val rideAdapter by lazy {
        UserRideAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnDoneRide.setOnClickListener(this@RideAngkotFragment)
        initData()
        initUI()
    }

    private fun initData() {
        dropAdapter.data.clear()
        dropAdapter.data.addAll(calonUser)

        rideAdapter.data.clear()
        rideAdapter.data.addAll(calonUser)
    }

    private fun initUI() = with(binding) {
        rvRideUser.adapter = rideAdapter
        rvDropUSer.adapter = dropAdapter
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnDoneRide -> {
                pref.isRidingAngkot = false
                activity?.finish()
            }
        }
    }
}
