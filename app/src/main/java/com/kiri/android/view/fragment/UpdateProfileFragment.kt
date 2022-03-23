package com.kiri.android.view.fragment

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.gson.Gson
import com.kiri.account.R
import com.kiri.account.data.models.ProfileData
import com.kiri.account.data.models.UpdateProfileBody
import com.kiri.account.databinding.UpdateProfileFragmentBinding
import com.kiri.account.presentation.viewmodel.AccountResource
import com.kiri.account.presentation.viewmodel.AccountViewModel
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
import java.text.SimpleDateFormat
import java.util.*

class UpdateProfileFragment :
    Fragment(R.layout.update_profile_fragment),
    View.OnClickListener,
    AccountResource {
    private val binding by viewBinding<UpdateProfileFragmentBinding>()
    private val pref: PrefUseCase by inject()
    private val viewModel by viewModel<AccountViewModel> { parametersOf(lifecycle, this) }
    private lateinit var body: UpdateProfileBody

    private var errorMessage: String? = null
    private val name = MutableStateFlow("")
    private val birthDate = MutableStateFlow("")
    private val email = MutableStateFlow("")
    private val phone = MutableStateFlow("")

    private val formIsValid = combine(
        name,
        birthDate,
        email,
        phone
    ) { args: Array<String> ->
        val nameIsValid = args[0].isNotEmpty()
        val birthDateIsValid = args[1].isNotEmpty()
        val emailIsValid =
            Patterns.EMAIL_ADDRESS.matcher(args[2])
                .matches()
        val phoneIsValid =
            Patterns.PHONE.matcher(args[3]).matches()
        validatorForm(
            emailIsValid,
            phoneIsValid
        )
        nameIsValid and birthDateIsValid and emailIsValid and phoneIsValid
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.back_toolbar)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnUpdate.setOnClickListener(this@UpdateProfileFragment)
            etBirthDate.setOnClickListener(this@UpdateProfileFragment)
        }
        formEditText()
        validationButton()
        getData()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnUpdate -> {
                bodyProfile()
                viewModel.doUpdate(body)
            }
            R.id.etBirthDate -> {
                val builder = MaterialDatePicker.Builder.datePicker()
                val picker = builder.setSelection(System.currentTimeMillis()).build()
                picker.show(childFragmentManager, picker.toString())
                picker.addOnPositiveButtonClickListener {
                    val sdf = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
                    val date = sdf.format(it)
                    binding.etBirthDate.setText(date)
                }
            }
        }
    }

    private fun getData() {
        binding.apply {
            val data = Gson().fromJson(pref.accountData, ProfileData::class.java)
            etFullName.setText(data.name)
            etBirthDate.setText(data.birthdate)
            etEmail.setText(data.email)
            etPhone.setText(data.phone)
        }
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

    private fun validatorForm(
        emailIsValid: Boolean,
        phoneIsValid: Boolean
    ) {
        binding.apply {
            if (emailIsValid.not() && !etEmail.text.isNullOrEmpty()) {
                errorMessage = "email tidak valid"
                tlEmail.error = errorMessage
            } else {
                tlEmail.error = null
            }
            if (phoneIsValid.not() && !etPhone.text.isNullOrEmpty()) {
                errorMessage = "nomor HP tidak valid"
                tlPhone.error = errorMessage
            } else {
                tlPhone.error = null
            }
        }
    }

    private fun bodyProfile() = with(binding) {
        body = UpdateProfileBody(
            name = etFullName.text.toString(),
            email = etEmail.text.toString(),
            birthdate = etBirthDate.text.toString(),
            noHp = etPhone.text.toString(),
            role = "supir"
        )
    }

    override fun onUpdateProfileLoading() {
        super.onUpdateProfileLoading()
        disableBtn(binding.btnUpdate)
    }

    override fun onUpdateProfileSuccess(data: ProfileData?) {
        super.onUpdateProfileSuccess(data)
        enableBtn(binding.btnUpdate)
        val profileData = Gson().toJson(data)
        pref.accountData = profileData

        shortToast(requireContext(), getString(R.string.label_success_update))
    }

    override fun onUpdateProfileFailed(error: String?) {
        super.onUpdateProfileFailed(error)
        enableBtn(binding.btnUpdate)
        if (error != null) {
            shortToast(requireContext(), error)
        }
    }
}
