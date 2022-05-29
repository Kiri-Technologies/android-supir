package com.kiri.android.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kiri.android.R
import com.kiri.android.databinding.BoardHistoryFragmentBinding
import com.kiri.android.view.adapter.EarningListAdapter
import com.kiri.common.utils.ApiResponse
import com.kiri.common.utils.shortToast
import com.kiri.trip.data.models.RiwayatNarikData
import com.kiri.trip.presentation.viewmodel.AngkotResource
import com.kiri.trip.presentation.viewmodel.AngkotViewModel
import com.kiri.ui.gone
import com.kiri.ui.visible
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class EarningListFragment : Fragment(R.layout.board_history_fragment), AngkotResource {
    private val binding by viewBinding<BoardHistoryFragmentBinding>()
    private val args: EarningListFragmentArgs by navArgs()
    private val viewModel by viewModel<AngkotViewModel> { parametersOf(lifecycle, this) }
    private val adapter by lazy {
        EarningListAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initUI()
        initAction()
    }

    private fun initData() {
        viewModel.getRideHistory(
            args.angkotId,
            args.supirId
        )
    }

    private fun initUI() = with(binding) {
        tvLabel.text = getString(R.string.label_angkot_earning)
        rvContent.adapter = adapter
    }

    private fun initAction() {
        adapter.setOnItemClickListener { _, _, _ ->
            findNavController().navigate(EarningListFragmentDirections.actionEarningListFragmentToCreateEarningsFragment())
        }
    }

    override fun onRideHistoryLoading() {
        super.onRideHistoryLoading()
        binding.rvContent.gone()
    }

    override fun onRideHistorySuccess(data: ApiResponse<List<RiwayatNarikData>>?) {
        super.onRideHistorySuccess(data)
        val item = data?.dataData?.firstOrNull()
        binding.apply {
            rvContent.visible()
            progressBar.gone()
            tvVehicleId.text = item?.vehicle?.platNomor
            val route = "${item?.vehicle?.route?.titikAwal} - ${item?.vehicle?.route?.titikAkhir}"
            tvTripRoute.text = route
        }
        adapter.data.clear()
        data?.dataData?.forEach {
            if (it.jumlahPendapatan == null) {
                adapter.addData(it)
            }
        }
    }

    override fun onRideHistoryFailed(error: String?) {
        super.onRideHistoryFailed(error)
        binding.rvContent.visible()
        binding.progressBar.gone()
        shortToast(requireContext(), "Tidak ada data")
    }
}
