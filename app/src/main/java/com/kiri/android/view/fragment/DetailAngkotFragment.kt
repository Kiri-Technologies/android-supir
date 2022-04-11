package com.kiri.android.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.gson.Gson
import com.kiri.account.data.models.ProfileData
import com.kiri.android.R
import com.kiri.android.databinding.DetailAngkotFragmentBinding
import com.kiri.android.view.adapter.FeedbackAdapter
import com.kiri.android.view.adapter.RideHistoryAdapter
import com.kiri.android.view.adapter.TripAngkotAdapter
import com.kiri.common.domain.PrefUseCase
import com.kiri.common.utils.ApiResponse
import com.kiri.trip.data.models.RiwayatNarikData
import com.kiri.trip.data.models.TripHistoryData
import com.kiri.trip.presentation.viewmodel.AngkotResource
import com.kiri.trip.presentation.viewmodel.AngkotViewModel
import com.kiri.ui.visible
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailAngkotFragment : Fragment(R.layout.detail_angkot_fragment), AngkotResource {
    private val binding by viewBinding<DetailAngkotFragmentBinding>()
    private val viewModel by viewModel<AngkotViewModel> { parametersOf(lifecycle, this) }
    private val args: DetailAngkotFragmentArgs by navArgs()
    private val pref by inject<PrefUseCase>()
    private val rideHistoryAdapter by lazy {
        RideHistoryAdapter()
    }
    private val tripAngkotAdapter by lazy {
        TripAngkotAdapter()
    }
    private val feedbackAdapter by lazy {
        FeedbackAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).apply {
            supportActionBar?.setHomeAsUpIndicator(R.drawable.back_toolbar)
            return super.onCreateView(inflater, container, savedInstanceState)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initUI()
        initAction()
    }

    private fun initData() {
        val angkotId = args.angkotConfirmData.id.toString()
        val supir = Gson().fromJson(pref.accountData, ProfileData::class.java)
        supir.id?.let {
            viewModel.getRideHistory(
                angkotId,
                it
            )
            viewModel.getTripByAngkot(1, angkotId, it)
        }
    }

    private fun initUI() = with(binding) {
        val titikAwal = args.angkotConfirmData.vehicle?.route?.titikAwal
        val titikAkhir = args.angkotConfirmData.vehicle?.route?.titikAkhir
        tvVehicleId.text = args.angkotConfirmData.vehicle?.platNomor
        tvTripRoute.text = String.format("$titikAwal - $titikAkhir")

        rvRideHistory.adapter = rideHistoryAdapter
        rvTripAngkot.adapter = tripAngkotAdapter
        rvFeedback.adapter = feedbackAdapter
    }

    private fun initAction() = with(binding) {
        tvTripHistoryMore.setOnClickListener {
            findNavController().navigate(
                DetailAngkotFragmentDirections.actionDetailAngkotFragmentToAngkotHistoryFragment(
                    tripAngkotAdapter.data.toTypedArray()
                )
            )
        }
        tvRideMore.setOnClickListener {
            findNavController().navigate(
                DetailAngkotFragmentDirections.actionDetailAngkotFragmentToRideHistoryFragment(
                    rideHistoryAdapter.data.toTypedArray()
                )
            )
        }
        tvFeedbackMore.setOnClickListener {
            findNavController().navigate(
                DetailAngkotFragmentDirections.actionDetailAngkotFragmentToFeedbackDetailFragment(
                    tripAngkotAdapter.data.toTypedArray()
                )
            )
        }
        btnQr.setOnClickListener {
            findNavController().navigate(
                DetailAngkotFragmentDirections.actionDetailAngkotFragmentToQrCodeFragment(
                    tripAngkotAdapter.data.firstOrNull()?.vehicle?.qrCode.toString()
                )
            )
        }
    }

    override fun onTripAngkotHistoryLoading() {
        super.onTripAngkotHistoryLoading()
    }

    override fun onTripAngkotHistorySuccess(data: ApiResponse<List<TripHistoryData>>?) {
        super.onTripAngkotHistorySuccess(data)
        data?.dataData?.let {
            tripAngkotAdapter.data.clear()
            feedbackAdapter.data.clear()
            tripAngkotAdapter.addData(it.take(10))
            feedbackAdapter.addData(it.take(5))
            if (!it.isNullOrEmpty()) {
                binding.tvTripHistoryMore.visible()
                binding.tvFeedbackMore.visible()
            }
        }
    }

    override fun onTripAngkotHistoryFailed(error: String?) {
        super.onTripAngkotHistoryFailed(error)
    }

    override fun onRideHistoryLoading() {
        super.onRideHistoryLoading()
    }

    override fun onRideHistorySuccess(data: ApiResponse<List<RiwayatNarikData>>?) {
        super.onRideHistorySuccess(data)
        data?.dataData?.let {
            rideHistoryAdapter.data.clear()
            rideHistoryAdapter.addData(it.take(10))
            if (!it.isNullOrEmpty()) binding.tvRideMore.visible()
        }
    }

    override fun onRideHistoryFailed(error: String?) {
        super.onRideHistoryFailed(error)
    }
}
