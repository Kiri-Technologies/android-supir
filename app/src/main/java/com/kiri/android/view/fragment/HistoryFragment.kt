package com.kiri.android.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.gson.Gson
import com.kiri.account.data.models.ProfileData
import com.kiri.android.R
import com.kiri.android.databinding.FragmentHistoryBinding
import com.kiri.android.view.adapter.HistoryAdapter
import com.kiri.common.domain.PrefUseCase
import com.kiri.common.utils.ApiResponse
import com.kiri.common.utils.shortToast
import com.kiri.trip.data.models.TripHistoryData
import com.kiri.trip.presentation.viewmodel.AngkotResource
import com.kiri.trip.presentation.viewmodel.AngkotViewModel
import com.kiri.ui.gone
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HistoryFragment : Fragment(R.layout.fragment_history), AngkotResource {
    private val binding by viewBinding<FragmentHistoryBinding>()
    private val viewModel by viewModel<AngkotViewModel> {
        parametersOf(lifecycle, this)
    }
    private val pref by inject<PrefUseCase>()

    private val rvAdapter by lazy {
        HistoryAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initUI()
        initAction()
    }

    private fun initData() {
        val getPref = pref.accountData
        val data = Gson().fromJson(getPref, ProfileData::class.java)
        data.id?.let { viewModel.getTripHistory(it) }
    }

    private fun initUI() {
        binding.rvContent.apply {
            adapter = rvAdapter
        }
    }

    private fun initAction() {
    }

    override fun onTripHistorySuccess(data: ApiResponse<List<TripHistoryData>>?) {
        super.onTripHistorySuccess(data)
        binding.progressBar.gone()
        data?.dataData?.map {
            rvAdapter.addData(it)
        }
    }

    override fun onTripHistoryFailed(error: String?) {
        super.onTripHistoryFailed(error)
        binding.progressBar.gone()
        error?.let { shortToast(requireContext(), it) }
    }
}
