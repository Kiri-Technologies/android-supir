package com.kiri.android.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kiri.android.R
import com.kiri.android.databinding.AngkotTripHistoryFragmentBinding
import com.kiri.android.view.adapter.AngkotTripHistoryAdapter
import com.kiri.common.utils.ApiResponse
import com.kiri.common.utils.shortToast
import com.kiri.trip.data.models.TripHistoryData
import com.kiri.trip.presentation.viewmodel.AngkotResource
import com.kiri.trip.presentation.viewmodel.AngkotViewModel
import com.kiri.ui.gone
import com.kiri.ui.visible
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AngkotAngkotHistoryFragment : Fragment(R.layout.angkot_trip_history_fragment), AngkotResource {

    private val binding by viewBinding<AngkotTripHistoryFragmentBinding>()
    private val viewModel by viewModel<AngkotViewModel> { parametersOf(lifecycle, this) }
    private val rvAdapter by lazy {
        AngkotTripHistoryAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initUI()
    }

    private fun initData() {
        viewModel.getTripByAngkot("1")
    }

    private fun initUI() {
        binding.rvContent.adapter = rvAdapter
    }

    override fun onTripAngkotHistoryLoading() {
        super.onTripAngkotHistoryLoading()
        binding.llContent.gone()
    }

    override fun onTripAngkotHistorySuccess(data: ApiResponse<List<TripHistoryData>>?) {
        super.onTripAngkotHistorySuccess(data)
        binding.progressBar.gone()
        binding.llContent.visible()
        data?.dataData?.map {
            rvAdapter.addData(it)
        }
        val item = data?.dataData?.firstOrNull()
        binding.tvVehicleId.text = item?.vehicle?.platNomor
        val route = "${item?.vehicle?.route?.titikAwal} - ${item?.vehicle?.route?.titikAkhir}"
        binding.tvTripRoute.text = route
    }

    override fun onTripAngkotHistoryFailed(error: String?) {
        super.onTripAngkotHistoryFailed(error)
        binding.progressBar.gone()
        error?.let { shortToast(requireContext(), it) }
    }
}
