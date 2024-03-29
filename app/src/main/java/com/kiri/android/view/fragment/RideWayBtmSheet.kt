package com.kiri.android.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import com.kiri.account.data.models.ProfileData
import com.kiri.android.R
import com.kiri.android.databinding.DialogRadioBinding
import com.kiri.android.utils.currentTime
import com.kiri.android.view.activity.RideAngkotActivity
import com.kiri.common.domain.PrefUseCase
import com.kiri.common.utils.ApiResponse
import com.kiri.common.utils.shortToast
import com.kiri.trip.data.models.CreateHistoryData
import com.kiri.trip.data.models.RoutesData
import com.kiri.trip.data.models.SetWayBody
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
    private lateinit var body: SetWayBody
    private var routeId: Int? = null
    private var way: String? = null

    companion object {
        const val TAG = "Pilih Rute"
        const val ANGKOTID = "angkotId"
        const val ROUTEID = "routeId"
        const val IS_BEROPERASI = "1"
    }

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
        routeId = arguments?.getInt(ROUTEID)
        viewModel.getRoutes(routeId.toString())
    }

    private fun initUI() = with(binding) {
        this@RideWayBtmSheet.isCancelable = false
        btnCancel.setOnClickListener {
            this@RideWayBtmSheet.dismiss()
        }
        rGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbAwal -> {
                    btnChoose.isEnabled = true
                    setBody(rbAwal.text.toString())
                    way = rbAwal.text.toString()
                }
                R.id.rbAkhir -> {
                    btnChoose.isEnabled = true
                    setBody(rbAkhir.text.toString())
                    way = rbAkhir.text.toString()
                }
            }
        }
        btnChoose.setOnClickListener {
            val supir = Gson().fromJson(pref.accountData, ProfileData::class.java)
            viewModel.rideAngkot(
                angkotId ?: "", IS_BEROPERASI, supir.id ?: "", currentTime(), body
            )
//            shortToast(requireContext(), way ?: "")
        }
    }

    private fun goToNarik(data: ApiResponse<CreateHistoryData>?) {
        this.dismiss()
        pref.angkotId = angkotId
        pref.routeId = routeId.toString()
        pref.histoyId = data?.dataData?.id.toString()
        pref.way = way
        startActivity(
            Intent(
                requireContext(),
                RideAngkotActivity::class.java
            )
        )
    }

    private fun setBody(arah: String) {
        body = SetWayBody(
            angkotId ?: "",
            arah,
            true,
            routeId.toString()
        )
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
        this.dismiss()
    }

    override fun onReadyRideLoading() {
        super.onReadyRideLoading()
        binding.btnChoose.setLoading(true)
        binding.btnCancel.isEnabled = false
    }

    override fun onReadyRideSuccess(data: ApiResponse<CreateHistoryData>?) {
        super.onReadyRideSuccess(data)
        binding.btnChoose.setLoading(false)
        goToNarik(data)
        binding.btnCancel.isEnabled = true
    }

    override fun onReadyRideFailed(error: String?) {
        super.onReadyRideFailed(error)
        binding.btnChoose.setLoading(false)
        shortToast(requireContext(), getString(R.string.error_message))
        binding.btnCancel.isEnabled = true
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

    override fun onStop() {
        super.onStop()
        binding.rGroup.clearCheck()
    }
}
