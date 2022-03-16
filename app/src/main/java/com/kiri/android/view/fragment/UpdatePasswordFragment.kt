package com.kiri.android.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kiri.account.data.models.ProfileData
import com.kiri.account.databinding.UpdatePasswordFragmentBinding
import com.kiri.account.presentation.viewmodel.AccountResource
import com.kiri.account.presentation.viewmodel.AccountViewModel
import com.kiri.android.R
import com.kiri.android.view.activity.AuthActivity
import com.kiri.common.data.pref.PrefKey
import com.kiri.common.domain.PrefUseCase
import com.kiri.common.utils.shortToast
import com.kiri.ui.disableBtn
import com.kiri.ui.enableBtn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class UpdatePasswordFragment :
    Fragment(R.layout.update_password_fragment),
    View.OnClickListener,
    AccountResource {

    private val binding by viewBinding<UpdatePasswordFragmentBinding>()
    private val viewModel by viewModel<AccountViewModel> { parametersOf(lifecycle, this) }
    private val pref by inject<PrefUseCase>()

    private val password = MutableStateFlow("")
    private val confirmPass = MutableStateFlow("")
    private var errorMessage: String? = null

    private val formIsValid = combine(
        password,
        confirmPass
    ) { password, confirmPass ->
        val passwordIsValid = password.length >= 6
        val passwordIsCorrect = confirmPass == password
        binding.apply {
            if (passwordIsValid.not() && !etPassword.text.isNullOrEmpty()) {
                errorMessage = "password kurang dari 6 karakter"
                tlPassword.error = errorMessage
            } else {
                tlPassword.error = null
            }
            if (passwordIsCorrect.not() && !etConfirmPassword.text.isNullOrEmpty()) {
                errorMessage = "password tidak sama"
                tlConfirmPassword.error = errorMessage
            } else {
                tlConfirmPassword.error = null
            }
        }
        passwordIsValid and passwordIsCorrect
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).apply {
            supportActionBar?.setHomeAsUpIndicator(R.drawable.back_toolbar)
            return super.onCreateView(inflater, container, savedInstanceState)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        formEditText()
        validationButton()

        binding.apply {
            btnUpdate.setOnClickListener(this@UpdatePasswordFragment)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnUpdate -> {
                viewModel.doUpdatePassword(binding.etPassword.text.toString())
            }
        }
    }

    private fun formEditText() = with(binding) {
        etPassword.doOnTextChanged { text, _, _, _ ->
            password.value = text.toString()
        }
        etConfirmPassword.doOnTextChanged { text, _, _, _ ->
            confirmPass.value = text.toString()
        }
    }

    private fun validationButton() {
        lifecycleScope.launchWhenStarted {
            formIsValid
                .collect {
                    binding.apply {
                        btnUpdate.isEnabled = it
                    }
                }
        }
    }

    override fun onUpdatePasswordLoading() {
        super.onUpdatePasswordLoading()
        disableBtn(binding.btnUpdate)
    }

    override fun onUpdatePasswordSuccess(data: ProfileData?) {
        super.onUpdatePasswordSuccess(data)
        shortToast(requireContext(), getString(R.string.label_success_update_password))

        startActivity(Intent(requireContext(), AuthActivity::class.java))
        pref.removeByKey(PrefKey.PROFILE)
        pref.removeByKey(PrefKey.TOKEN)

        activity?.finishAffinity()
    }

    override fun onUpdatePasswordFailed(error: String?) {
        super.onUpdatePasswordFailed(error)
        enableBtn(binding.btnUpdate)
        shortToast(requireContext(), getString(R.string.error_message))
    }
}
