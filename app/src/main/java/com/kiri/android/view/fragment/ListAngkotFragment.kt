package com.kiri.android.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.gson.Gson
import com.kiri.account.data.models.ProfileData
import com.kiri.android.R
import com.kiri.android.databinding.ListAngkotFragmentBinding
import com.kiri.android.view.adapter.ListAngkotAdapter
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

class ListAngkotFragment :
    Fragment(R.layout.list_angkot_fragment),
    AngkotResource,
    SearchView.OnQueryTextListener {

    private val binding by viewBinding<ListAngkotFragmentBinding>()
    private val viewModel by viewModel<AngkotViewModel> { parametersOf(lifecycle, this) }
    private var angkotList = ArrayList<AngkotConfirmData>()
    private val pref: PrefUseCase by inject()
    private val rvAdapter by lazy {
        ListAngkotAdapter()
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

    private fun initUI() {
        binding.rvContent.layoutManager = LinearLayoutManager(requireContext())
        binding.rvContent.adapter = rvAdapter
        rvAdapter.setEmptyView(R.layout.empty_view_item)
    }

    private fun initAction() {
        binding.searchView.setOnQueryTextListener(this)
        rvAdapter.setOnItemClickListener { adapter, _, position ->
            val data: AngkotConfirmData = adapter.data[position] as AngkotConfirmData
            findNavController().navigate(
                AngkotTabFragmentDirections.actionNavigationAngkotToDetailAngkotFragment(
                    data
                )
            )
        }
    }

    private fun filter(text: String) {
        val filtered: ArrayList<AngkotConfirmData> = ArrayList()
        for (angkotData in angkotList) {
            val angkotRoute =
                "${angkotData.vehicle?.route?.titikAwal?.lowercase()} - ${angkotData.vehicle?.route?.titikAkhir?.lowercase()}"
            if (angkotData.vehicle?.platNomor?.lowercase()?.contains(text.lowercase()) == true) {
                filtered.add(angkotData)
            } else if (angkotRoute.contains(text.lowercase())) {
                filtered.add(angkotData)
            }
        }
        rvAdapter.data.clear()
        rvAdapter.addData(filtered)
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
                if (item.isConfirmed == 1 && item.vehicle != null) {
                    angkotList.add(item)
                }
            }
            rvAdapter.addData(angkotList)
        }
    }

    override fun onGetAngkotFailed(error: String?) {
        super.onGetAngkotFailed(error)
        shortToast(requireContext(), getString(R.string.error_message))
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        filter(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText.isNullOrEmpty()) {
            rvAdapter.data.clear()
            rvAdapter.addData(angkotList)
        }
        return true
    }
}
