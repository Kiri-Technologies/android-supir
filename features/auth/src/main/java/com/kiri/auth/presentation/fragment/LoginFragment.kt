package com.kiri.auth.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kiri.auth.R
import com.kiri.auth.databinding.LoginFragmentBinding

class LoginFragment : Fragment(R.layout.login_fragment), View.OnClickListener {

    private val binding by viewBinding<LoginFragmentBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvGoToRegister.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvGoToRegister -> {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }
    }
}
