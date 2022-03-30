package com.kiri.android.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.kiri.android.R
import com.kiri.android.databinding.FragmentAngkotTabBinding
import com.kiri.android.view.adapter.AngkotTabAdapter

class AngkotTabFragment : Fragment(R.layout.fragment_angkot_tab) {

    private val binding by viewBinding<FragmentAngkotTabBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        val tabTitles =
            arrayOf(getString(R.string.label_angkot), getString(R.string.label_confirm))
        val tabAdapter = AngkotTabAdapter(childFragmentManager, lifecycle)
        binding.viewPager.adapter = tabAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }
}
