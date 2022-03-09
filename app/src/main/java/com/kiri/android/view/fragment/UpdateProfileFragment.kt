package com.kiri.android.view.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import coil.transform.CircleCropTransformation
import com.google.gson.Gson
import com.kiri.account.R
import com.kiri.account.data.models.ProfileData
import com.kiri.account.databinding.UpdateProfileFragmentBinding
import com.kiri.common.domain.PrefUseCase
import com.kiri.common.utils.shortToast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import org.koin.android.ext.android.inject

class UpdateProfileFragment : Fragment(R.layout.update_profile_fragment), View.OnClickListener {
    private val binding by viewBinding<UpdateProfileFragmentBinding>()
    private val pref: PrefUseCase by inject()
    private lateinit var permissionResult: ActivityResultLauncher<String>
    private val pictureResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            picture(uri)
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
        val passwordIsValid = args[4].length >= 6
        val passwordIsCorrect = args[4] == args[5]
        validatorForm(
            emailIsValid,
            phoneIsValid,
            passwordIsValid,
            passwordIsCorrect
        )
        nameIsValid and birthDateIsValid and emailIsValid and phoneIsValid and passwordIsValid and
            passwordIsCorrect
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
            ivAccount.setOnClickListener(this@UpdateProfileFragment)
        }
        permissionGranted()
        formEditText()
        validationButton()
        getData()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnUpdate -> {
            }
            R.id.ivAccount -> {
                permission()
            }
        }
    }

    private fun permission() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) -> {
                pictureResult.launch("image/*")
            }
            else -> {
                permissionResult.launch(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            }
        }
    }

    private fun getData() {
        binding.apply {
            val data = Gson().fromJson(pref.accountData, ProfileData::class.java)
            etFullName.setText(data.name)
            etBirthDate.setText(data.birthdate)
            etEmail.setText(data.email)
            etPhone.setText(data.noHp)
        }
    }

    private fun picture(uri: Uri?) {
        if (uri == null) {
            binding.ivAccount.setImageResource(R.drawable.profile)
            binding.iconAdd.visibility = View.VISIBLE
        } else {
            binding.ivAccount.load(uri) {
                placeholder(R.drawable.profile)
                transformations(CircleCropTransformation())
            }
            binding.iconAdd.visibility = View.GONE
        }
    }

    private fun permissionGranted() {
        permissionResult =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) { // Do something if permission granted
                    pictureResult.launch("image/*")
                } else { // Do something as the permission is not granted
                    shortToast(requireContext(), "Perlu Akses File")
                }
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

    private fun validatorForm(
        emailIsValid: Boolean,
        phoneIsValid: Boolean,
        passwordIsValid: Boolean,
        passwordIsCorrect: Boolean
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
}
