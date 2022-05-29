package com.kiri.android.view.fragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.gson.Gson
import com.kiri.account.data.models.ProfileData
import com.kiri.android.R
import com.kiri.android.databinding.DetailAngkotFragmentBinding
import com.kiri.android.model.Score
import com.kiri.android.view.adapter.FeedbackAdapter
import com.kiri.android.view.adapter.RideHistoryAdapter
import com.kiri.android.view.adapter.TripAngkotAdapter
import com.kiri.common.domain.PrefUseCase
import com.kiri.common.utils.ApiResponse
import com.kiri.common.utils.shortToast
import com.kiri.common.utils.toFormatRupiah
import com.kiri.trip.data.models.EarningsByTodayData
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

    private var scoreList = ArrayList<Score>()
    private var angkotId: String = ""
    private var supirId: String = ""

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
        angkotId = args.angkotConfirmData.id.toString()
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
    }

    private fun initBarChart() = with(binding) {
//        hide grid lines
        barChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = barChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        // remove right y-axis
        barChart.axisRight.isEnabled = false

        // remove legend
        barChart.legend.isEnabled = false

        // remove description label
        barChart.description.isEnabled = false

        // add animation
        barChart.animateY(3000)

        // to draw label on xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = MyAxisFormatter()
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
    }

    inner class MyAxisFormatter : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            return if (index < scoreList.size) {
                scoreList[index].name
            } else {
                ""
            }
        }
    }

    private fun chartData() = with(binding) {
        // now draw bar chart with dynamic data
        val entries: ArrayList<BarEntry> = ArrayList()

        // you can replace this data object with  your custom object
        for (i in scoreList.indices) {
            val score = scoreList[i]
            entries.add(BarEntry(i.toFloat(), score.earnings.toFloat()))
        }

        val barDataSet = BarDataSet(entries, "")
        barDataSet.color = resources.getColor(R.color.color_primary, null)
        val data = BarData(barDataSet)
        barChart.data = data
        barChart.invalidate()
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
        if (supirId.isNotEmpty() && angkotId.isNotEmpty()) {
            btnCreateEarnings.setOnClickListener {
                findNavController().navigate(
                    DetailAngkotFragmentDirections.actionDetailAngkotFragmentToEarningListFragment(
                        angkotId, supirId
                    )
                )
            }
        }
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
            tripAngkotAdapter.addData(it.take(10))
            feedbackAdapter.addData(it.take(5))
            if (!it.isNullOrEmpty()) {
                binding.tvTripHistoryMore.visible()
                binding.tvFeedbackMore.visible()
            }
        }
        val qr = data?.dataData?.firstOrNull()?.vehicle?.qrCode.toString()
        qrCodeToBrowser(qr)
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
            rideHistoryAdapter.addData(it.take(10))
            if (!it.isNullOrEmpty()) binding.tvRideMore.visible()
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
        scoreList.clear()
        data?.dataData?.day1?.let { Score("Senin", it) }?.let { scoreList.add(it) }
        data?.dataData?.day2?.let { Score("Selasa", it) }?.let { scoreList.add(it) }
        data?.dataData?.day3?.let { Score("Rabu", it) }?.let { scoreList.add(it) }
        data?.dataData?.day4?.let { Score("Kamis", it) }?.let { scoreList.add(it) }
        data?.dataData?.day5?.let { Score("Jumat", it) }?.let { scoreList.add(it) }
        data?.dataData?.day6?.let { Score("Sabtu", it) }?.let { scoreList.add(it) }
        data?.dataData?.day7?.let { Score("Minggu", it) }?.let { scoreList.add(it) }

        val allEarning = scoreList.all { it.earnings == 0 }
        if (!allEarning) {
            initBarChart()
            chartData()
        }
    }
}
