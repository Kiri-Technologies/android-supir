package com.kiri.android.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kiri.android.R
import com.kiri.android.databinding.DialogRadioBinding
import com.kiri.android.view.activity.RideAngkotActivity
import com.kiri.common.domain.PrefUseCase
import com.kiri.common.utils.ApiResponse
import com.kiri.common.utils.shortToast
import com.kiri.trip.data.models.RoutesData
import com.kiri.trip.presentation.viewmodel.AngkotResource
import com.kiri.trip.presentation.viewmodel.AngkotViewModel
import com.kiri.ui.gone
import com.kiri.ui.visible
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class RideWayBtmSheet : BottomSheetDialogFragment(), AngkotResource {
    private lateinit var binding: DialogRadioBinding
    private lateinit var viewModel: AngkotViewModel
    private val pref by inject<PrefUseCase>()
    private var angkotId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogRadioBinding.inflate(inflater, container, false)
        initData()
        initUI()
        return binding.root
    }

    private fun initData() {
        viewModel = getViewModel { parametersOf(lifecycle, this) }
        angkotId = arguments?.getString(ANGKOTID)
        viewModel.getRoutes(angkotId ?: "0")
    }

    private fun initUI() = with(binding) {
        btnCancel.setOnClickListener {
            this@RideWayBtmSheet.dismiss()
        }
        rGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbAwal -> {
                    btnChoose.isEnabled = true
                }
                R.id.rbAkhir -> {
                    btnChoose.isEnabled = true
                }
            }
        }
    }

    private fun goToNarik() {
        pref.isRidingAngkot = true
        pref.angkotId = angkotId
        startActivity(
            Intent(
                requireContext(),
                RideAngkotActivity::class.java
            )
        )
    }

    companion object {
        const val TAG = "Pilih Rute"
        const val ANGKOTID = "angkotId"
    }

    override fun onGetRoutesSuccess(data: ApiResponse<RoutesData>?) {
        super.onGetRoutesSuccess(data)
        unloadingView()

        binding.rbAwal.text = data?.dataData?.titikAwal
        binding.rbAkhir.text = data?.dataData?.titikAkhir
    }

    override fun onGetRoutesLoading() {
        super.onGetRoutesLoading()
        loadingView()
    }

    override fun onGetRoutesFailed(error: String?) {
        super.onGetRoutesFailed(error)
        shortToast(requireContext(), getString(R.string.error_message))
    }

    private fun unloadingView() {
        binding.apply {
            loading.gone()
            rbAwal.visible()
            rbAkhir.visible()
            containerButton.visible()
        }
    }

    private fun loadingView() {
        binding.apply {
            loading.visible()
            rbAwal.visibility = View.INVISIBLE
            rbAkhir.visibility = View.INVISIBLE
            containerButton.visibility = View.INVISIBLE
        }
    }
}
