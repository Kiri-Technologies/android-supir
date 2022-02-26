package com.kiri.auth.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kiri.auth.R
import com.kiri.auth.databinding.RegisterFragmentBinding

class RegisterFragment : Fragment(R.layout.register_fragment), View.OnClickListener {
    private val binding by viewBinding<RegisterFragmentBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvGoToLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvGoToLogin -> {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }
    }
}
