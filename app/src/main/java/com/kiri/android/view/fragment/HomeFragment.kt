package com.kiri.android.view.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.gson.Gson
import com.kiri.account.data.models.ProfileData
import com.kiri.android.R
import com.kiri.android.data.chartData
import com.kiri.android.databinding.HomeFragmentBinding
import com.kiri.android.widget.initBarChart
import com.kiri.common.data.model.Earning
import com.kiri.common.domain.PrefUseCase
import com.kiri.common.utils.ApiResponse
import com.kiri.trip.data.models.EarningsByTodayData
import com.kiri.trip.presentation.viewmodel.AngkotResource
import com.kiri.trip.presentation.viewmodel.AngkotViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HomeFragment : Fragment(R.layout.home_fragment), AngkotResource {
    private val binding by viewBinding<HomeFragmentBinding>()
    private val pref: PrefUseCase by inject()
    private val viewModel by viewModel<AngkotViewModel> { parametersOf(lifecycle, this) }
    private lateinit var profile: ProfileData
    private var earningList = ArrayList<Earning>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initUI()
        initAction()
    }

    private fun initData() {
        profile = Gson().fromJson(pref.accountData, ProfileData::class.java)
        profile.id?.let {
            viewModel.getAvgUser(it)
            viewModel.getUserToday(it)
            viewModel.getEarningsToday("", it)
        }
    }

    private fun initUI() {
        (requireActivity() as AppCompatActivity).title =
            getString(R.string.title_home)
        binding.tvName.text = profile.name
    }

    private fun initAction() {}

    override fun onAvgUserSuccess(data: ApiResponse<Int>?) {
        super.onAvgUserSuccess(data)
        binding.tvAvgUser.text = data?.dataData.toString()
    }

    override fun onAUserTodaySuccess(data: ApiResponse<Int>?) {
        super.onAUserTodaySuccess(data)
        binding.tvUserToday.text = data?.dataData.toString()
    }

    override fun onEarningsTodaySuccess(data: ApiResponse<EarningsByTodayData>?) {
        super.onEarningsTodaySuccess(data)
        earningList.clear()
        data?.dataData?.day1?.let { Earning("Senin", it) }?.let { earningList.add(it) }
        data?.dataData?.day2?.let { Earning("Selasa", it) }?.let { earningList.add(it) }
        data?.dataData?.day3?.let { Earning("Rabu", it) }?.let { earningList.add(it) }
        data?.dataData?.day4?.let { Earning("Kamis", it) }?.let { earningList.add(it) }
        data?.dataData?.day5?.let { Earning("Jumat", it) }?.let { earningList.add(it) }
        data?.dataData?.day6?.let { Earning("Sabtu", it) }?.let { earningList.add(it) }
        data?.dataData?.day7?.let { Earning("Minggu", it) }?.let { earningList.add(it) }

        val allEarning = earningList.all { it.earnings == 0 }
        if (!allEarning) {
            binding.barChart.apply {
                initBarChart(earningList)
                chartData(earningList)
            }
        }
    }
}
