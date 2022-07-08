package com.kiri.android.view.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
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
import com.kiri.common.data.pref.PrefKey
import com.kiri.common.domain.PrefUseCase
import com.kiri.common.utils.ApiResponse
import com.kiri.common.utils.Resource
import com.kiri.common.utils.shortToast
import com.kiri.trip.data.models.LocationBody
import com.kiri.trip.data.models.RoutesData
import com.kiri.trip.data.models.ToggleFullBody
import com.kiri.trip.data.models.ToggleStopBody
import com.kiri.trip.presentation.viewmodel.AngkotResource
import com.kiri.trip.presentation.viewmodel.AngkotViewModel
import com.kiri.ui.gone
import com.kiri.ui.showDialog
import com.kiri.ui.showDialogList
import com.kiri.ui.visible
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.concurrent.TimeUnit

class RideAngkotFragment :
    Fragment(R.layout.fragment_ride_angkot),
    View.OnClickListener,
    AngkotResource {
    private val binding by viewBinding<FragmentRideAngkotBinding>()
    private val pref by inject<PrefUseCase>()
    private val viewModel by viewModel<AngkotViewModel> { parametersOf(lifecycle, this) }
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
        initObserver()
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

    private fun initObserver() {
        viewModel.angkotDistance(pref.angkotId ?: "").observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {}
                Resource.Status.SUCCESS -> {
                    val distance = it.data?.jarak_antar_angkot_km ?: 0.0
                    val time = it.data?.jarak_antar_angkot_waktu
                    binding.tvDistance.text = String.format("$distance KM")
                    binding.tvTime.text = String.format("$time Menit")

                    if (distance > 0.0 && distance <= 0.5) {
                        binding.tvLocationName.text = ""
                    }
                }
                Resource.Status.ERROR -> {}
            }
        }
    }

    @SuppressLint("InlinedApi")
    private fun location() {
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        locationRequest = LocationRequest.create().apply {
            // Sets the desired interval for
            // active location updates.
            // This interval is inexact.
            interval = TimeUnit.SECONDS.toMillis(7)

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
                    viewModel.setLocation(
                        LocationBody(
                            pref.angkotId,
                            it.latitude.toString(),
                            it.longitude.toString()
                        )
                    )
                    toggleNgetem(it.latitude.toString(), it.longitude.toString())
                    toggleFullAngkot()
                    viewModel.getRoutes(pref.angkotId ?: "")
                } ?: shortToast(requireContext(), getString(R.string.error_message))
            }
        }
    }

    private fun locationNotNeeded(): Task<Void> {
        return fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    private fun finishRide() {
        pref.removeByKey(PrefKey.ANGKOT_ID)
        pref.removeByKey(PrefKey.ROUTE_ID)
        activity?.finish()
        locationNotNeeded()
    }

    private fun toggleNgetem(lat: String, long: String) {
        binding.swNgetem.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) viewModel.toggleNgetem(
                ToggleStopBody(
                    pref.angkotId, pref.routeId, lat, long, true
                )
            ) else {
                viewModel.toggleNgetem(
                    ToggleStopBody(
                        pref.angkotId, pref.routeId, lat, long, false
                    )
                )
            }
        }
    }

    private fun toggleFullAngkot() {
        binding.swFull.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) viewModel.toggleFull(
                ToggleFullBody(
                    pref.angkotId, pref.routeId, true
                )
            ) else {
                viewModel.toggleFull(
                    ToggleFullBody(
                        pref.angkotId, pref.routeId, false
                    )
                )
            }
        }
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

    override fun onGetRoutesSuccess(data: ApiResponse<RoutesData>?) {
        super.onGetRoutesSuccess(data)

        // current
        val currentLat = currentLocation?.latitude ?: 0.0
        val currentLong = currentLocation?.longitude ?: 0.0

        // Awal
        val latAwal = data?.dataData?.latTitikAwal?.toDouble() ?: 0.0
        val ngecekLatAwal =
            currentLat < latAwal + 0.0001 * 5 && currentLat > latAwal - 0.0001 * 5

        val longAwal = data?.dataData?.longTitikAwal?.toDouble() ?: 0.0
        val ngecekLongAwal =
            currentLong < longAwal + 0.0001 * 5 && currentLong > longAwal - 0.0001 * 5

        // Akhir
        val latAkhir = data?.dataData?.latTitikAkhir?.toDouble() ?: 0.0
        val ngecekLatAkhir =
            currentLat < latAkhir + 0.0001 * 5 && currentLat > latAkhir - 0.0001 * 5

        val longAkhir = data?.dataData?.longTitikAkhir?.toDouble() ?: 0.0
        val ngecekLongAkhir =
            currentLong < longAkhir + 0.0001 * 5 && currentLong > longAkhir - 0.0001 * 5

        if (ngecekLatAwal && ngecekLongAwal) {
            locationVisible()
            binding.tvLocationName.text =
                data?.dataData?.titikAwal ?: ""
        } else if (ngecekLatAkhir && ngecekLongAkhir) {
            locationVisible()
            binding.tvLocationName.text =
                data?.dataData?.titikAkhir ?: ""
        } else {
            locationGone()
        }
    }

    private fun locationGone() {
        binding.tvLabelLoc.gone()
        binding.tvLocationName.gone()
    }

    private fun locationVisible() {
        binding.tvLabelLoc.visible()
        binding.tvLocationName.visible()
    }
}
