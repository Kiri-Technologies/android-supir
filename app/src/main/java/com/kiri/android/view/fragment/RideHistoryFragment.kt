package com.kiri.android.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kiri.android.R
import com.kiri.android.databinding.BoardHistoryFragmentBinding
import com.kiri.android.view.adapter.RideHistoryDetailAdapter
import com.kiri.ui.gone

class RideHistoryFragment : Fragment(R.layout.board_history_fragment) {
    private val args by navArgs<RideHistoryFragmentArgs>()
    private val binding by viewBinding<BoardHistoryFragmentBinding>()
    private val adapter by lazy {
        RideHistoryDetailAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initUI()
    }

    private fun initData() {
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
}
