package com.kiri.android.view.fragment

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.gson.Gson
import com.kiri.account.data.models.ProfileData
import com.kiri.android.R
import com.kiri.android.data.chartData
import com.kiri.android.databinding.DetailAngkotFragmentBinding
import com.kiri.android.utils.day1
import com.kiri.android.utils.day2
import com.kiri.android.utils.day3
import com.kiri.android.utils.day4
import com.kiri.android.utils.day5
import com.kiri.android.utils.day6
import com.kiri.android.utils.initBarChart
import com.kiri.android.utils.now
import com.kiri.android.view.adapter.FeedbackAdapter
import com.kiri.android.view.adapter.RideHistoryAdapter
import com.kiri.android.view.adapter.TripAngkotAdapter
import com.kiri.common.data.model.Earning
import com.kiri.common.domain.PrefUseCase
import com.kiri.common.utils.ApiResponse
import com.kiri.common.utils.permission
import com.kiri.common.utils.shortToast
import com.kiri.common.utils.toFormatRupiah
import com.kiri.trip.data.models.EarningsByTodayData
import com.kiri.trip.data.models.PremiumData
import com.kiri.trip.data.models.RiwayatNarikData
import com.kiri.trip.data.models.TripHistoryData
import com.kiri.trip.domain.usecase.models.TotalEarningsDomain
import com.kiri.trip.presentation.viewmodel.AngkotResource
import com.kiri.trip.presentation.viewmodel.AngkotViewModel
import com.kiri.ui.gone
import com.kiri.ui.visible
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailAngkotFragment :
    Fragment(R.layout.detail_angkot_fragment),
    AngkotResource,
    View.OnClickListener {
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
    private val btmSheet by lazy {
        RideWayBtmSheet()
    }
    private var earningList = ArrayList<Earning>()
    private var angkotId: String = ""
    private var supirId: String = ""
    private var routeId: Int? = null

    private val permissionResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                shortToast(requireContext(), "Akses Lokasi Nyala, Klik sekali lagi")
            } else {
                shortToast(requireContext(), "Akses Lokasi Tidak Nyala")
            }
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
        angkotId = args.angkotConfirmData.angkotId.toString()
        routeId = args.angkotConfirmData.vehicle?.route?.id
        val supir = Gson().fromJson(pref.accountData, ProfileData::class.java)
        supir.id?.let {
            viewModel.getRideHistory(
                angkotId,
                it
            )
            viewModel.getTripByAngkot(1, angkotId, it)
            viewModel.getTotalEarnings(angkotId, it)
            viewModel.getTodayEarnings(angkotId, it)
            viewModel.getEarningsToday(angkotId, it)
            viewModel.premium(it)
            supirId = it
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

        rideHistoryAdapter.setEmptyView(R.layout.empty_view_item)
        tripAngkotAdapter.setEmptyView(R.layout.empty_view_item)
        feedbackAdapter.setEmptyView(R.layout.empty_view_item)
        Log.d("PREF", pref.histoyId ?: "")
        qrCodeToBrowser(args.angkotConfirmData.vehicle?.qrCode ?: "")
    }

    private fun initAction() = with(binding) {
        tvTripHistoryMore.setOnClickListener(this@DetailAngkotFragment)
        tvRideMore.setOnClickListener(this@DetailAngkotFragment)
        tvFeedbackMore.setOnClickListener(this@DetailAngkotFragment)
        if (supirId.isNotEmpty() && angkotId.isNotEmpty()) {
            btnCreateEarnings.setOnClickListener(this@DetailAngkotFragment)
        }
        btnNarik.setOnClickListener(this@DetailAngkotFragment)
    }

    override fun onTripAngkotHistoryLoading() {
        super.onTripAngkotHistoryLoading()
        binding.rvTripAngkot.gone()
        binding.rvFeedback.gone()
    }

    override fun onTripAngkotHistorySuccess(data: ApiResponse<List<TripHistoryData>>?) {
        super.onTripAngkotHistorySuccess(data)
        binding.rvTripAngkot.visible()
        binding.rvFeedback.visible()
        binding.pbTripAngkot.gone()
        binding.pbFeedback.gone()
        data?.dataData?.let {
            tripAngkotAdapter.data.clear()
            feedbackAdapter.data.clear()
            tripAngkotAdapter.addData(it)
            if (it.firstOrNull()?.feedback != null) feedbackAdapter.addData(it)
            if (it.isNotEmpty()) {
                binding.tvTripHistoryMore.visible()
                binding.tvFeedbackMore.visible()
            }
        }
    }

    private fun qrCodeToBrowser(qr: String) {
        binding.btnQr.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(qr))
            try {
                startActivity(browserIntent)
            } catch (ex: ActivityNotFoundException) {
                shortToast(requireContext(), getString(R.string.label_empty_data))
            }
        }
    }

    override fun onRideHistoryLoading() {
        super.onRideHistoryLoading()
        binding.rvRideHistory.gone()
    }

    override fun onRideHistorySuccess(data: ApiResponse<List<RiwayatNarikData>>?) {
        super.onRideHistorySuccess(data)
        binding.rvRideHistory.visible()
        binding.pbRideHistory.gone()
        data?.dataData?.let {
            rideHistoryAdapter.data.clear()
            rideHistoryAdapter.addData(it)
            if (it.isNotEmpty()) binding.tvRideMore.visible()
        }
    }

    override fun onTotalEarningsLoading() {
        super.onTotalEarningsLoading()
        binding.tvTotalEarnings.gone()
    }

    override fun onTotalEarningsSuccess(data: ApiResponse<TotalEarningsDomain>?) {
        super.onTotalEarningsSuccess(data)
        with(binding) {
            pbTotalEarnings.gone()
            tvTotalEarnings.visible()
            tvTotalEarnings.text = data?.dataData?.totalPendapatan?.toLong()?.toFormatRupiah()
        }
    }

    override fun onTotalEarningsFailed(error: String?) {
        super.onTotalEarningsFailed(error)
        binding.pbTotalEarnings.gone()
        binding.tvTotalEarnings.visible()
        binding.tvTotalEarnings.text = "---"
    }

    override fun onTodayEarningsLoading() {
        super.onTodayEarningsLoading()
        binding.tvTodayEarnings.gone()
    }

    override fun onTodayEarningsSuccess(data: ApiResponse<Int>?) {
        super.onTodayEarningsSuccess(data)
        with(binding) {
            tvTodayEarnings.visible()
            pbTodayEarnings.gone()
            tvTodayEarnings.text = data?.dataData?.toLong()?.toFormatRupiah()
        }
    }

    override fun onTodayEarningsFailed(error: String?) {
        super.onTodayEarningsFailed(error)
        binding.tvTodayEarnings.visible()
        binding.pbTodayEarnings.gone()
        binding.tvTodayEarnings.text = "---"
    }

    override fun onEarningsTodaySuccess(data: ApiResponse<EarningsByTodayData>?) {
        super.onEarningsTodaySuccess(data)
        earningList.clear()
        data?.dataData?.day7?.let { Earning(day6(), it) }?.let { earningList.add(it) }
        data?.dataData?.day6?.let { Earning(day5(), it) }?.let { earningList.add(it) }
        data?.dataData?.day5?.let { Earning(day4(), it) }?.let { earningList.add(it) }
        data?.dataData?.day4?.let { Earning(day3(), it) }?.let { earningList.add(it) }
        data?.dataData?.day3?.let { Earning(day2(), it) }?.let { earningList.add(it) }
        data?.dataData?.day2?.let { Earning(day1(), it) }?.let { earningList.add(it) }
        data?.dataData?.day1?.let { Earning(now(), it) }?.let { earningList.add(it) }

        val allEarning = earningList.all { it.earnings == 0 }
        if (!allEarning) {
            binding.barChart.apply {
                initBarChart(earningList)
                chartData(earningList)
            }
        }
    }

    private fun btnNarikDisable() {
        binding.btnNarik.isEnabled = false
        binding.btnNarik.setCardBackgroundColor(
            resources.getColor(
                R.color.button_grey,
                requireContext().theme
            )
        )
    }

    private fun btnNarikEnable() {
        binding.btnNarik.isEnabled = true
        binding.btnNarik.setCardBackgroundColor(
            resources.getColor(
                R.color.color_primary,
                requireContext().theme
            )
        )
    }

    override fun onPremiumLoading() {
        super.onPremiumLoading()
        btnNarikDisable()
    }

    override fun onPremiumSuccess(data: ApiResponse<PremiumData>?) {
        super.onPremiumSuccess(data)
        if (data?.dataData?.isPremium == true) {
            btnNarikEnable()
            binding.tvLabelPremium.gone()
        } else {
            btnNarikDisable()
            binding.tvLabelPremium.visible()
        }
    }

    override fun onClick(v: View?) {
        with(binding) {
            when (v) {
                tvTripHistoryMore -> findNavController().navigate(
                    DetailAngkotFragmentDirections.actionDetailAngkotFragmentToAngkotHistoryFragment(
                        tripAngkotAdapter.data.toTypedArray()
                    )
                )
                tvRideMore -> findNavController().navigate(
                    DetailAngkotFragmentDirections.actionDetailAngkotFragmentToRideHistoryFragment(
                        rideHistoryAdapter.data.toTypedArray()
                    )
                )
                tvFeedbackMore -> findNavController().navigate(
                    DetailAngkotFragmentDirections.actionDetailAngkotFragmentToFeedbackDetailFragment(
                        tripAngkotAdapter.data.toTypedArray()
                    )
                )
                btnCreateEarnings -> findNavController().navigate(
                    DetailAngkotFragmentDirections.actionDetailAngkotFragmentToEarningListFragment(
                        angkotId, supirId
                    )
                )
                btnNarik -> {
                    if (args.angkotConfirmData.vehicle?.isBeroperasi == 1) {
                        shortToast(requireContext(), "Maaf angkot sedang ditarik supir lain")
                    } else {
                        requireContext().permission(
                            permissionResult,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) {
                            btmSheetShow()
                        }
                    }
                }
                else -> {}
            }
        }
    }

    private fun btmSheetShow() {
        val bundle = Bundle()
        bundle.putString(RideWayBtmSheet.ANGKOTID, angkotId)
        bundle.putInt(RideWayBtmSheet.ROUTEID, routeId ?: 0)
        btmSheet.arguments = bundle
        btmSheet.show(childFragmentManager, RideWayBtmSheet.TAG)
    }
}
