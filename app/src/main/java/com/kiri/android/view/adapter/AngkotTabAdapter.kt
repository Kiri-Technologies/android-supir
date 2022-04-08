package com.kiri.android.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kiri.android.view.fragment.AngkotConfirmationFragment
import com.kiri.android.view.fragment.ListAngkotFragment

class AngkotTabAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragment = listOf<Fragment>(
        ListAngkotFragment(),
        AngkotConfirmationFragment()
    )

    override fun getItemCount() = fragment.size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> fragment[0]
            1 -> fragment[1]
            else -> fragment[0]
        }
    }
}
