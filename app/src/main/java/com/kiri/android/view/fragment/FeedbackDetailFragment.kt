package com.kiri.android.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kiri.android.R
import com.kiri.android.databinding.FeedbackDetailFragmentBinding
import com.kiri.android.view.adapter.FeedbackDetailAdapter

class FeedbackDetailFragment : Fragment(R.layout.feedback_detail_fragment) {
    private val binding by viewBinding<FeedbackDetailFragmentBinding>()
    private val args by navArgs<FeedbackDetailFragmentArgs>()
    private val adapter by lazy {
        FeedbackDetailAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initUI()
    }

    private fun initData() {
        args.tripData?.map {
            adapter.addData(it)
        }
    }

    private fun initUI() = with(binding) {
        rvContent.adapter = adapter
    }
}
