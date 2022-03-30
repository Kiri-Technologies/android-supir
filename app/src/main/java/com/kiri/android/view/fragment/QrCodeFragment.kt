package com.kiri.android.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kiri.android.R
import com.kiri.android.databinding.FragmentQrCodeBinding

class QrCodeFragment : Fragment(R.layout.fragment_qr_code) {

    private val binding by viewBinding<FragmentQrCodeBinding>()
    private val args by navArgs<QrCodeFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.qrCode
    }
}
