package com.kiri.android.view.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kiri.account.domain.usecase.model.ProfDom
import com.kiri.account.presentation.viewmodel.AccountResource
import com.kiri.account.presentation.viewmodel.AccountViewModel
import com.kiri.android.R
import com.kiri.android.data.chartData
import com.kiri.android.data.day1
import com.kiri.android.data.day2
import com.kiri.android.data.day3
import com.kiri.android.data.day4
import com.kiri.android.data.day5
import com.kiri.android.data.day6
import com.kiri.android.data.now
import com.kiri.android.databinding.HomeFragmentBinding
import com.kiri.android.widget.initBarChart
import com.kiri.common.data.model.Earning
import com.kiri.common.utils.ApiResponse
import com.kiri.trip.data.models.EarningsByTodayData
import com.kiri.trip.presentation.viewmodel.AngkotResource
import com.kiri.trip.presentation.viewmodel.AngkotViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HomeFragment :
    Fragment(R.layout.home_fragment),
    AngkotResource,
    AccountResource,
    View.OnClickListener {
    private val binding by viewBinding<HomeFragmentBinding>()
    private val viewModel by viewModel<AngkotViewModel> { parametersOf(lifecycle, this) }
    private val viewModelProfile: AccountViewModel by viewModel {
        parametersOf(lifecycle, this)
    }
    private var earningList = ArrayList<Earning>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initUI()
        initAction()
    }

    private fun initData() {
        viewModelProfile.getProfile()
    }

    private fun initUI() {
        (requireActivity() as AppCompatActivity).title =
            getString(R.string.title_home)
    }

    private fun initAction() {
        binding.tvRide.setOnClickListener(this)
    }

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

    override fun onProfileSuccess(data: ProfDom?) {
        super.onProfileSuccess(data)
        if (data != null) {
            viewModel.getAvgUser(data.id)
            viewModel.getUserToday(data.id)
            viewModel.getEarningsToday("", data.id)
        }
        binding.tvName.text = data?.name
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.tvRide -> findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToNavigationAngkot())
        }
    }
}
