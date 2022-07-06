package com.kiri.android.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kiri.android.R
import com.kiri.android.databinding.BoardHistoryFragmentBinding
import com.kiri.android.view.adapter.RideHistoryDetailAdapter
import com.kiri.common.utils.shortToast
import com.kiri.trip.data.models.RiwayatNarikData
import com.kiri.ui.gone

class RideHistoryFragment : Fragment(R.layout.board_history_fragment) {
    private val args by navArgs<RideHistoryFragmentArgs>()
    private val binding by viewBinding<BoardHistoryFragmentBinding>()
    private val adapter by lazy {
        RideHistoryDetailAdapter()
    }
    private var earningId: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initUI()
        initAction()
    }

    private fun initData() {
        adapter.data.clear()
        args.riwayatNarikData?.map {
            adapter.addData(it)
        }
    }

    private fun initUI() = with(binding) {
        progressBar.gone()
        rvContent.adapter = adapter
        val item = args.riwayatNarikData?.firstOrNull()
        tvVehicleId.text = item?.vehicle?.platNomor
        val route = "${item?.vehicle?.route?.titikAwal} - ${item?.vehicle?.route?.titikAkhir}"
        tvTripRoute.text = route
    }

    private fun initAction() {
        adapter.setOnItemChildClickListener { adapter, _, position ->
            val data = adapter.data[position] as RiwayatNarikData
            earningId = data.id.toString()
            if (earningId.isEmpty()) {
                shortToast(requireContext(), "Catatan Tidak Ditemukan")
            } else findNavController().navigate(
                RideHistoryFragmentDirections.actionRideHistoryFragmentToCreateEarningsFragment(
                    earningId
                )
            )
        }
    }
}
