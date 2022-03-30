package com.kiri.android.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kiri.android.R
import com.kiri.android.databinding.ListAngkotFragmentBinding
import com.kiri.android.view.adapter.ListAngkotAdapter
import com.kiri.common.utils.ApiResponse
import com.kiri.trip.data.models.AngkotData
import com.kiri.trip.presentation.viewmodel.AngkotResource
import com.kiri.trip.presentation.viewmodel.AngkotViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ListAngkotFragment :
    Fragment(R.layout.list_angkot_fragment),
    AngkotResource,
    SearchView.OnQueryTextListener {

    private val binding by viewBinding<ListAngkotFragmentBinding>()
    private val viewModel by viewModel<AngkotViewModel> { parametersOf(lifecycle, this) }
    private var angkotList = ArrayList<AngkotData>()
    private val rvAdapter by lazy {
        ListAngkotAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initUI()
        initAction()
    }

    private fun initData() {
        viewModel.getAngkot("hermanli-VyLUuH")
    }

    private fun initUI() {
        binding.rvContent.layoutManager = LinearLayoutManager(requireContext())
        binding.rvContent.adapter = rvAdapter
    }

    private fun initAction() {
        binding.searchView.setOnQueryTextListener(this)
        binding.searchView.setOnCloseListener {
            rvAdapter.data.clear()
            rvAdapter.addData(angkotList)
            false
        }
    }

    private fun filter(text: String) {
        val filtered: ArrayList<AngkotData> = ArrayList()
        for (angkotData in angkotList) {
            if (angkotData.platNomor?.lowercase()?.contains(text.lowercase()) == true) {
                filtered.add(angkotData)
            }
        }
        rvAdapter.data.clear()
        rvAdapter.addData(filtered)
    }

    override fun onGetAngkotLoading() {
        super.onGetAngkotLoading()
    }

    override fun onGetAngkotSuccess(data: ApiResponse<List<AngkotData>>?) {
        super.onGetAngkotSuccess(data)
        data?.dataData?.let {
            angkotList.addAll(it)
            rvAdapter.addData(angkotList)
        }
    }

    override fun onGetAngkotFailed(error: String?) {
        super.onGetAngkotFailed(error)
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
