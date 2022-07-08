package com.kiri.android.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.gson.Gson
import com.kiri.account.data.models.ProfileData
import com.kiri.android.R
import com.kiri.android.databinding.AngkotConfirmationFragmentBinding
import com.kiri.android.view.adapter.AngkotConfirmationAdapter
import com.kiri.common.domain.PrefUseCase
import com.kiri.common.utils.ApiResponse
import com.kiri.common.utils.shortToast
import com.kiri.trip.data.models.AngkotConfirmData
import com.kiri.trip.presentation.viewmodel.AngkotResource
import com.kiri.trip.presentation.viewmodel.AngkotViewModel
import com.kiri.ui.gone
import com.kiri.ui.visible
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AngkotConfirmationFragment : Fragment(R.layout.angkot_confirmation_fragment), AngkotResource {
    private val binding by viewBinding<AngkotConfirmationFragmentBinding>()
    private val viewModel by viewModel<AngkotViewModel> { parametersOf(lifecycle, this) }
    private val pref by inject<PrefUseCase>()
    private var angkotList = ArrayList<AngkotConfirmData>()
    private val rvAdapter by lazy {
        AngkotConfirmationAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initUI()
        initAction()
    }

    override fun onResume() {
        super.onResume()
        initData()
    }

    private fun initData() {
        val profile = Gson().fromJson(pref.accountData, ProfileData::class.java)
        profile.id?.let { viewModel.getAngkot(it) }
    }

    private fun initUI() = with(binding) {
        rvContent.layoutManager = LinearLayoutManager(requireContext())
        rvContent.adapter = rvAdapter
        rvAdapter.setEmptyView(R.layout.empty_view_item)
    }

    private fun initAction() {
        rvAdapter.setOnItemChildClickListener { adapter, view, position ->
            val data = adapter.data[position] as AngkotConfirmData
            when (view.id) {
                R.id.btnCancel -> {
                    viewModel.doConfirmAngkot(data.id.toString(), 0)
                    adapter.removeAt(position)
                }
                R.id.btnAcc -> {
                    viewModel.doConfirmAngkot(data.id.toString(), 1)
                    adapter.removeAt(position)
                }
            }
        }
    }

    override fun onGetAngkotLoading() {
        super.onGetAngkotLoading()
        binding.rvContent.gone()
        binding.progressBar.visible()
    }

    override fun onGetAngkotSuccess(data: ApiResponse<List<AngkotConfirmData>>?) {
        super.onGetAngkotSuccess(data)
        binding.rvContent.visible()
        binding.progressBar.gone()
        rvAdapter.data.clear()
        angkotList.clear()
        data?.dataData?.let {
            for (item in it) {
                if (item.isConfirmed == null) {
                    angkotList.add(item)
                }
            }
            rvAdapter.addData(angkotList)
        }
    }

    override fun onConfirmAngkotSuccess(data: ApiResponse<List<Nothing>>?) {
        super.onConfirmAngkotSuccess(data)
        shortToast(requireContext(), "Supir Berhasil Mengkonfirmasi")
    }

    override fun onConfirmAngkotFailed(error: String?) {
        super.onConfirmAngkotFailed(error)
        shortToast(requireContext(), getString(R.string.error_message))
    }
}
