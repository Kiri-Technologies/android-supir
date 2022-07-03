package com.kiri.android.view.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.Task
import com.kiri.android.R
import com.kiri.android.data.calonUser
import com.kiri.android.data.userNaik
import com.kiri.android.databinding.FragmentRideAngkotBinding
import com.kiri.android.view.adapter.DropUserAdapter
import com.kiri.android.view.adapter.UserRideAdapter
import com.kiri.common.domain.PrefUseCase
import com.kiri.common.utils.shortToast
import com.kiri.ui.showDialog
import com.kiri.ui.showDialogList
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

class RideAngkotFragment : Fragment(R.layout.fragment_ride_angkot), View.OnClickListener {
    private val binding by viewBinding<FragmentRideAngkotBinding>()
    private val pref by inject<PrefUseCase>()
    private val dropAdapter by lazy {
        DropUserAdapter()
    }
    private val rideAdapter by lazy {
        UserRideAdapter()
    }

    // FusedLocationProviderClient - Main class for receiving location updates.
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    // LocationRequest - Requirements for the location updates, i.e.,
// how often you should receive updates, the priority, etc.
    private lateinit var locationRequest: LocationRequest

    // LocationCallback - Called when FusedLocationProviderClient
// has a new Location
    private lateinit var locationCallback: LocationCallback

    // This will store current location info
    private var currentLocation: Location? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initUI()
        initAction()
    }

    private fun initData() {
        dropAdapter.data.clear()
        dropAdapter.data.addAll(calonUser)

        rideAdapter.data.clear()
        rideAdapter.data.addAll(userNaik)
    }

    private fun initUI() = with(binding) {
        rvRideUser.adapter = rideAdapter
        rvDropUSer.adapter = dropAdapter

        locationCallback()
        location()
        locationPermissionCheck()
    }

    private fun initAction() = with(binding) {
        btnDoneRide.setOnClickListener(this@RideAngkotFragment)
        containerDropPassenger.setOnClickListener(this@RideAngkotFragment)
        containerRidePassenger.setOnClickListener(this@RideAngkotFragment)
    }

    @SuppressLint("InlinedApi")
    private fun location() {
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        locationRequest = LocationRequest.create().apply {
            // Sets the desired interval for
            // active location updates.
            // This interval is inexact.
            interval = TimeUnit.SECONDS.toMillis(5)

            // Sets the fastest rate for active location updates.
            // This interval is exact, and your application will never
            // receive updates more frequently than this value
            TimeUnit.SECONDS.toMillis(30)

            // Sets the maximum time when batched location
            // updates are delivered. Updates may be
            // delivered sooner than this interval
            TimeUnit.MINUTES.toMillis(2)

            priority = Priority.PRIORITY_HIGH_ACCURACY
        }
    }

    private fun locationPermissionCheck() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()
        )
    }

    private fun locationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let {
                    currentLocation = it
                    binding.tvLat.text = it.latitude.toString()
                    binding.tvLong.text = it.longitude.toString()
                    it.latitude - 0.0001 * 5
                    it.longitude - 0.0001 * 5
                    // use latitude and longitude as per your need
                } ?: shortToast(requireContext(), getString(R.string.error_message))
            }
        }
    }

    private fun locationNotNeeded(): Task<Void> {
        return fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    private fun finishRide() {
        pref.isRidingAngkot = false
        activity?.finish()
        locationNotNeeded()
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnDoneRide -> {
                requireContext().showDialog(
                    message = getString(R.string.label_finish_ride_message),
                    positiveAction = { finishRide() },
                    negativeAction = {}
                )
            }
            binding.containerRidePassenger -> {
                requireContext().showDialogList(
                    rideAdapter,
                    getString(R.string.tittle_user_ride)
                )
            }
            binding.containerDropPassenger -> {
                requireContext().showDialogList(
                    dropAdapter,
                    getString(R.string.tittle_drop_user),
                    true
                )
            }
        }
    }
}
