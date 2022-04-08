package com.kiri.android.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kiri.android.R
import com.kiri.android.databinding.AngkotTripHistoryFragmentBinding
import com.kiri.android.view.adapter.AngkotTripHistoryAdapter
import com.kiri.trip.presentation.viewmodel.AngkotResource
import com.kiri.trip.presentation.viewmodel.AngkotViewModel
import com.kiri.ui.gone
import com.kiri.ui.visible
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AngkotHistoryFragment : Fragment(R.layout.angkot_trip_history_fragment), AngkotResource {

    private val binding by viewBinding<AngkotTripHistoryFragmentBinding>()
    private val viewModel by viewModel<AngkotViewModel> { parametersOf(lifecycle, this) }
    private val args: AngkotHistoryFragmentArgs by navArgs()
    private val rvAdapter by lazy {
        AngkotTripHistoryAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initUI()
    }

    private fun initData() {
        args.tripData?.map {
            rvAdapter.addData(it)
        }
    }

    private fun initUI() {
        binding.rvContent.adapter = rvAdapter
        binding.progressBar.gone()
        binding.llContent.visible()
        val item = args.tripData?.firstOrNull()
        binding.tvVehicleId.text = item?.vehicle?.platNomor
        val route = "${item?.vehicle?.route?.titikAwal} - ${item?.vehicle?.route?.titikAkhir}"
        binding.tvTripRoute.text = route
    }
}
