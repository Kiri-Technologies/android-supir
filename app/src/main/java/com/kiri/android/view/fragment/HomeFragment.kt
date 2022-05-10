package com.kiri.android.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.gson.Gson
import com.kiri.account.data.models.ProfileData
import com.kiri.android.R
import com.kiri.android.databinding.HomeFragmentBinding
import com.kiri.common.domain.PrefUseCase
import com.kiri.common.utils.ApiResponse
import com.kiri.trip.presentation.viewmodel.AngkotResource
import com.kiri.trip.presentation.viewmodel.AngkotViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HomeFragment : Fragment(R.layout.home_fragment), AngkotResource {
    private val binding by viewBinding<HomeFragmentBinding>()
    private val pref: PrefUseCase by inject()
    private val viewModel by viewModel<AngkotViewModel> { parametersOf(lifecycle, this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initUI()
        initAction()
    }

    private fun initData() {
        val angkot = Gson().fromJson(pref.accountData, ProfileData::class.java)
        angkot?.id?.let {
            viewModel.getAvgUser(it)
            viewModel.getUserToday(it)
        }
    }

    private fun initUI() {}
    private fun initAction() {}

    override fun onAvgUserSuccess(data: ApiResponse<Int>?) {
        super.onAvgUserSuccess(data)
        binding.tvAvgUser.text = data?.dataData.toString()
    }

    override fun onAUserTodaySuccess(data: ApiResponse<Int>?) {
        super.onAUserTodaySuccess(data)
        binding.tvUserToday.text = data?.dataData.toString()
    }
}
