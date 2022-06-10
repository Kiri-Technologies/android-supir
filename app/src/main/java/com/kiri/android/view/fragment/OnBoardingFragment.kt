package com.kiri.android.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.kiri.android.R
import com.kiri.android.databinding.FragmentOnBoardingBinding

class OnBoardingFragment : Fragment(R.layout.fragment_on_boarding) {

    private val binding by viewBinding(FragmentOnBoardingBinding::bind)
    private var page: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        page = arguments?.getInt("page", 0) ?: 0
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupWalkthrough(drawableId: Int) {
        binding.apply {
            Glide.with(requireContext())
                .load(drawableId)
                .into(imageOnboarding)
        }
    }

    private fun setupView() {
        when (page) {
            0 -> setupWalkthrough(
                drawableId = R.drawable.onboarding_image1,
            )
            1 -> setupWalkthrough(
                drawableId = R.drawable.onboarding_image3,
            )
        }
    }

    companion object {
        fun newInstance(page: Int) = OnBoardingFragment().apply {
            arguments = bundleOf("page" to page)
        }
    }
}
