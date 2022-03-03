package com.kiri.auth.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kiri.auth.R
import com.kiri.auth.data.models.LoginData
import com.kiri.auth.databinding.LoginFragmentBinding
import com.kiri.auth.presentation.viewmodel.AuthResource
import com.kiri.auth.presentation.viewmodel.AuthViewModel
import com.kiri.common.domain.PrefUseCaseImpl
import com.kiri.common.utils.shortToast
import com.kiri.ui.disableBtn
import com.kiri.ui.enableBtn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class LoginFragment : Fragment(R.layout.login_fragment), View.OnClickListener, AuthResource {

    private val binding by viewBinding<LoginFragmentBinding>()
    private val viewModel by viewModel<AuthViewModel> {
        parametersOf(lifecycle, this)
    }
    private val pref: PrefUseCaseImpl by inject()

    private var errorMessage: String? = null
    private val email = MutableStateFlow("")
    private val password = MutableStateFlow("")
    private val formIsValid = combine(
        email,
        password
    ) { email, password ->
        val emailIsValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val passwordIsValid = password.length > 6
        binding.apply {
            if (emailIsValid.not() && !etEmail.text.isNullOrEmpty()) {
                errorMessage = "email not valid"
                binding.tlEmail.error = errorMessage
            } else {
                binding.tlEmail.error = null
            }
            if (passwordIsValid.not() && !etPassword.text.isNullOrEmpty()) {
                errorMessage = "password kurang dari 6 karakter"
                binding.tlPassword.error = errorMessage
            } else {
                binding.tlPassword.error = null
            }
        }
        emailIsValid and passwordIsValid
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            tvGoToRegister.setOnClickListener(this@LoginFragment)
            btnLogin.setOnClickListener(this@LoginFragment)
        }
        formEditText()
        validation()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvGoToRegister -> {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
            R.id.btnLogin -> {
                binding.apply {
                    btnLogin.isEnabled = false
                    viewModel.login(etEmail.text.toString(), etPassword.text.toString())
                }
            }
        }
    }

    private fun formEditText() = with(binding) {
        etEmail.doOnTextChanged { text, _, _, _ ->
            email.value = text.toString()
        }
        etPassword.doOnTextChanged { text, _, _, _ ->
            password.value = text.toString()
        }
    }

    private fun validation() {
        lifecycleScope.launch {
            formIsValid
                .collect {
                    binding.apply {
                        if (it) {
                            errorMessage = null
                            tlEmail.error = errorMessage
                            tlPassword.error = errorMessage
                        }
                        btnLogin.isEnabled = it
                    }
                }
        }
    }

    override fun onLoginLoading() {
        super.onLoginLoading()
        disableBtn(binding.btnLogin)
    }

    override fun onLoginSuccess(data: LoginData?) {
        super.onLoginSuccess(data)
        enableBtn(binding.btnLogin)

        val intent = Intent(
            requireContext(),
            Class.forName("com.kiri.android.view.activity.HomeActivity")
        )

        pref.token = data?.tokenData?.originalData?.token
        startActivity(intent)
        activity?.finish()
    }

    override fun onLoginFailed(error: String?) {
        super.onLoginFailed(error)
        enableBtn(binding.btnLogin)
        if (error != null) {
            shortToast(requireContext(), error)
        }
    }
}
