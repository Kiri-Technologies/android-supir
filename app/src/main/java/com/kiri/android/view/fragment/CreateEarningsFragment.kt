package com.kiri.android.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kiri.android.R
import com.kiri.android.databinding.FragmentEarningsNoteBinding
import com.kiri.android.view.adapter.EarningsNoteAdapter
import com.kiri.ui.selectedBtn
import com.kiri.ui.unSelectedBtn

class CreateEarningsFragment : Fragment(R.layout.fragment_earnings_note) {
    private val binding by viewBinding<FragmentEarningsNoteBinding>()
    private val adapterBtn by lazy {
        EarningsNoteAdapter()
    }
    private lateinit var dataBtn: List<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initUI()
        initAction()
    }

    private fun initData() {
        dataBtn = listOf(
            "Rp. 20.000",
            "Rp. 40.000",
            "Rp. 50.000",
            "Rp. 70.000",
            "Rp. 100.000",
            "Rp. 150.000"
        )
    }

    private fun initUI() = with(binding) {
        rvMoney.layoutManager = GridLayoutManager(context, 2)
        rvMoney.adapter = adapterBtn
        adapterBtn.addData(dataBtn)
    }

    @SuppressLint("SetTextI18n")
    private fun initAction() {
        adapterBtn.setOnItemClickListener { adapter, _, position ->
            val data = adapter.data[position] as String
            binding.rvMoney.children.forEach {
                if (it.tag == data) {
                    context?.selectedBtn(it as Button)
                } else {
                    context?.unSelectedBtn(it as Button)
                }
            }
            binding.etEarnings.setText(data.substring(4))
        }
    }
}
