package com.kiri.android.view.fragment

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kiri.android.R

class RideAngkotFragment : Fragment(R.layout.fragment_ride_angkot) {

    override fun onResume() {
        super.onResume()
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
    }
}
