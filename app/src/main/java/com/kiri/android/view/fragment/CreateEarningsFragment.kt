package com.kiri.android.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.view.children
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kiri.android.R
import com.kiri.android.databinding.FragmentEarningsNoteBinding
import com.kiri.android.view.adapter.EarningsNoteAdapter
import com.kiri.common.utils.ApiResponse
import com.kiri.common.utils.shortToast
import com.kiri.trip.presentation.viewmodel.AngkotResource
import com.kiri.trip.presentation.viewmodel.AngkotViewModel
import com.kiri.ui.disableBtn
import com.kiri.ui.enableBtn
import com.kiri.ui.selectedBtn
import com.kiri.ui.unSelectedBtn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CreateEarningsFragment : Fragment(R.layout.fragment_earnings_note), AngkotResource {
    private val binding by viewBinding<FragmentEarningsNoteBinding>()
    private val arg by navArgs<CreateEarningsFragmentArgs>()
    private val viewModel by viewModel<AngkotViewModel> { parametersOf(lifecycle, this) }
    private val adapterBtn by lazy {
        EarningsNoteAdapter()
    }
    private lateinit var dataBtn: List<String>
    private var earning = MutableStateFlow("")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initUI()
        initAction()
    }

    private fun initData() {
        dataBtn = listOf(
            "20000",
            "40000",
            "50000",
            "70000",
            "100000",
            "150000"
        )
    }

    private fun initUI() = with(binding) {
        rvMoney.layoutManager = GridLayoutManager(context, 2)
        rvMoney.adapter = adapterBtn
        adapterBtn.addData(dataBtn)
        btnState()
    }

    private fun btnState() = with(binding) {
        etEarnings.doOnTextChanged { text, _, _, _ ->
            earning.value = text.toString()
        }
        lifecycleScope.launch {
            earning.collect {
                btnSave.isEnabled = it.isNotEmpty()
            }
        }
    }

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
            binding.etEarnings.setText(data)
        }
        submit()
    }

    private fun submit() = with(binding) {
        btnSave.setOnClickListener {
            viewModel.createEarning(arg.earningId, earning.value.toInt())
        }
    }

    override fun onCreateEarningLoading() {
        super.onCreateEarningLoading()
        disableBtn(binding.btnSave)
    }

    override fun onCreateEarningSuccess(data: ApiResponse<Nothing>?) {
        super.onCreateEarningSuccess(data)
        enableBtn(binding.btnSave)
        shortToast(requireContext(), "Data Berhasil Disimpan")
    }

    override fun onCreateEarningFailed(error: String?) {
        super.onCreateEarningFailed(error)
        enableBtn(binding.btnSave)
        shortToast(requireContext(), getString(R.string.error_message))
    }
}
