package com.kiri.android.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kiri.android.R
import com.kiri.android.databinding.FragmentHistoryBinding
import com.kiri.android.view.adapter.HistoryAdapter
import com.kiri.trip.presentation.viewmodel.TripResource
import com.kiri.trip.presentation.viewmodel.TripViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment(R.layout.fragment_history), TripResource {
    private val binding by viewBinding<FragmentHistoryBinding>()
    private val viewModel by viewModel<TripViewModel>()
    private val rvAdapter by lazy {
        HistoryAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvContent.apply {
            adapter = rvAdapter
        }
    }

    override fun onTripHistoryLoading() {
        super.onTripHistoryLoading()
    }

    override fun onTripHistorySuccess() {
        super.onTripHistorySuccess()
    }

    override fun onTripHistoryFailed(error: String?) {
        super.onTripHistoryFailed(error)
    }
}
