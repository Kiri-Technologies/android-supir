package com.kiri.auth.presentation.fragment

import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kiri.auth.R
import com.kiri.auth.data.models.RegisterBody
import com.kiri.auth.data.models.RegisterData
import com.kiri.auth.databinding.RegisterFragmentBinding
import com.kiri.auth.presentation.viewmodel.AuthResource
import com.kiri.auth.presentation.viewmodel.AuthViewModel
import com.kiri.common.utils.shortToast
import com.kiri.ui.disableBtn
import com.kiri.ui.enableBtn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class RegisterFragment : Fragment(R.layout.register_fragment), View.OnClickListener, AuthResource {

    private val binding by viewBinding<RegisterFragmentBinding>()
    private val viewModel by viewModel<AuthViewModel> {
        parametersOf(lifecycle, this)
    }

    private var errorMessage: String? = null
    private val name = MutableStateFlow("")
    private val birthDate = MutableStateFlow("")
    private val email = MutableStateFlow("")
    private val phone = MutableStateFlow("")
    private val password = MutableStateFlow("")
    private val confirmPass = MutableStateFlow("")

    private val formIsValid = combine(
        name,
        birthDate,
        email,
        phone,
        password,
        confirmPass
    ) { args: Array<String> ->
        val nameIsValid = args[0].isNotEmpty()
        val birthDateIsValid = args[1].isNotEmpty()
        val emailIsValid =
            Patterns.EMAIL_ADDRESS.matcher(args[2])
                .matches()
        val phoneIsValid =
            Patterns.PHONE.matcher(args[3]).matches()
        val passwordIsValid = args[4].length > 6
        val passwordIsCorrect = args[4] == args[5]
        validatorForm(
            nameIsValid,
            birthDateIsValid,
            emailIsValid,
            phoneIsValid,
        )
        nameIsValid and birthDateIsValid and emailIsValid and phoneIsValid and passwordIsValid and
            passwordIsCorrect
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvGoToLogin.setOnClickListener(this)
        binding.btnRegister.setOnClickListener(this)
        formEditText()
        validationButton()
    }

    private fun formEditText() = with(binding) {
        etFullName.doOnTextChanged { text, _, _, _ ->
            name.value = text.toString()
        }
        etBirthDate.doOnTextChanged { text, _, _, _ ->
            birthDate.value = text.toString()
        }
        etEmail.doOnTextChanged { text, _, _, _ ->
            email.value = text.toString()
        }
        etPhone.doOnTextChanged { text, _, _, _ ->
            phone.value = text.toString()
        }
        etPassword.doOnTextChanged { text, _, _, _ ->
            password.value = text.toString()
        }
        etConfirmPassword.doOnTextChanged { text, _, _, _ ->
            confirmPass.value = text.toString()
        }
    }

    private fun validationButton() {
        lifecycleScope.launch {
            formIsValid
                .collect {
                    binding.apply {
                        btnRegister.isEnabled = it
                    }
                }
        }
    }

    private fun validatorForm(
        emailIsValid: Boolean,
        phoneIsValid: Boolean,
        passwordIsValid: Boolean,
        passwordIsCorrect: Boolean
    ) {
        binding.apply {
            if (emailIsValid.not() && !binding.etEmail.text.isNullOrEmpty()) {
                errorMessage = "email tidak valid"
                tlEmail.error = errorMessage
            } else {
                tlEmail.error = null
            }
            if (phoneIsValid.not() && !binding.etPhone.text.isNullOrEmpty()) {
                errorMessage = "nomor HP tidak valid"
                tlPhone.error = errorMessage
            } else {
                tlPhone.error = null
            }
            if (passwordIsValid.not() && !binding.etPassword.text.isNullOrEmpty()) {
                errorMessage = "password kurang dari 6 karakter"
                tlPassword.error = errorMessage
            } else {
                tlPassword.error = null
            }
            if (passwordIsCorrect.not() && !binding.etConfirmPassword.text.isNullOrEmpty()) {
                errorMessage = "password tidak sama"
                tlConfirmPassword.error = errorMessage
            } else {
                tlConfirmPassword.error = null
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvGoToLogin -> {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
            R.id.btnRegister -> {
                submit()
            }
        }
    }

    private fun submit() = with(binding) {
        val name = etFullName.text.toString()
        val birthdate = etBirthDate.text.toString()
        val email = etEmail.text.toString()
        val phone = etPhone.text.toString()
        val password = etPassword.text.toString()

        val body = RegisterBody(
            name = name,
            birthdate = birthdate,
            email = email,
            phone = phone,
            password = password,
            role = "supir"
        )

        viewModel.register(body)
    }

    override fun onRegisterLoading() {
        super.onRegisterLoading()
        disableBtn(binding.btnRegister)
    }

    override fun onRegisterSuccess(data: RegisterData?) {
        super.onRegisterSuccess(data)
        disableBtn(binding.btnRegister)
        shortToast(requireContext(), "Register Sukses, Silahkan login")
    }

    override fun onRegisterFailed(error: String?) {
        super.onRegisterFailed(error)
        enableBtn(binding.btnRegister)

        if (error != null) {
            shortToast(requireContext(), error)
        }
    }
}
