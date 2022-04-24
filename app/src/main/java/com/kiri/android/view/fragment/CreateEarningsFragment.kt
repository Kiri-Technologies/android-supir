package com.kiri.android.view.fragment

import android.content.res.Resources.Theme
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.Button
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kiri.android.R
import com.kiri.android.databinding.FragmentEarningsNoteBinding
import com.kiri.common.utils.shortToast

class CreateEarningsFragment : Fragment(R.layout.fragment_earnings_note) {
    private val binding by viewBinding<FragmentEarningsNoteBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initUI()
        initAction()
    }

    private fun initData() {}
    private fun initUI() = with(binding) {
        val listBtn = listOf(
            R.id.btn20, R.id.btn40, R.id.btn50, R.id.btn70, R.id.btn100, R.id.btn150
        )
    }

    private fun selectedBtn(btn: Button) {
        val theme: Theme = ContextThemeWrapper(
            context,
            R.style.Theme_Kiri
        ).theme
        btn.setBackgroundColor(
            ResourcesCompat.getColor(
                resources,
                R.color.blue_color,
                theme
            )
        )
        btn.setTextColor(
            ResourcesCompat.getColor(
                resources,
                R.color.white,
                theme
            )
        )
    }

    private fun unSelectedBtn(btn: Button) {
        val theme: Theme = ContextThemeWrapper(
            context,
            R.style.Theme_Kiri
        ).theme
        btn.setBackgroundColor(
            ResourcesCompat.getColor(
                resources,
                R.color.white,
                theme
            )
        )
        btn.setTextColor(
            ResourcesCompat.getColor(
                resources,
                R.color.black,
                theme
            )
        )
    }

    private fun initAction() {}
}
