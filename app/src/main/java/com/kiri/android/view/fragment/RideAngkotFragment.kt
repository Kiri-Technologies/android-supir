package com.kiri.android.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kiri.android.R
import com.kiri.android.databinding.FragmentRideAngkotBinding

class RideAngkotFragment : Fragment(R.layout.fragment_ride_angkot), View.OnClickListener {
    private val binding by viewBinding<FragmentRideAngkotBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnDoneRide.setOnClickListener(this@RideAngkotFragment)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnDoneRide -> activity?.finish()
        }
    }
}
