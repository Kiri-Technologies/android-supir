package com.kiri.android.view.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.kiri.account.data.models.ProfileData
import com.kiri.account.databinding.DetailAccountFragmentBinding
import com.kiri.account.presentation.viewmodel.AccountResource
import com.kiri.account.presentation.viewmodel.AccountViewModel
import com.kiri.android.R
import com.kiri.common.BuildConfig
import com.kiri.common.domain.PrefUseCase
import com.kiri.common.utils.shortToast
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailAccountFragment :
    Fragment(R.layout.detail_account_fragment),
    View.OnClickListener,
    AccountResource {

    private val binding by viewBinding<DetailAccountFragmentBinding>()
    private val pref by inject<PrefUseCase>()
    private val viewModel by viewModel<AccountViewModel> { parametersOf(lifecycle, this) }
    private val permissionResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // Do something if permission granted
                pictureResult.launch("image/*")
            } else {
                // Do something as the permission is not granted
                shortToast(requireContext(), "Perlu Akses File")
            }
        }
    private val pictureResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            picture(uri)
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).apply {
            supportActionBar?.setHomeAsUpIndicator(R.drawable.back_toolbar)
            title = getString(R.string.title_navigation_detail)
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val data = Gson().fromJson(pref.accountData, ProfileData::class.java)
            tvId.text = data.id
            tvName.text = data.name
            tvBirthdate.text = data.birthdate
            tvEmail.text = data.email
            tvPhone.text = data.noHp
            Glide.with(requireContext())
                .load("${BuildConfig.BASE_URL}${data.image}")
                .placeholder(R.drawable.profile)
                .circleCrop()
                .into(binding.ivAccount)

            // Onclick
            btnUpdate.setOnClickListener(this@DetailAccountFragment)
            ivAccount.setOnClickListener(this@DetailAccountFragment)
            btnUpdatePassword.setOnClickListener(this@DetailAccountFragment)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnUpdate -> {
                findNavController().navigate(R.id.action_detailAccountFragment_to_updateProfileFragment)
            }
            R.id.ivAccount -> {
                permission()
            }
            R.id.btnUpdatePassword -> {
                findNavController().navigate(R.id.action_detailAccountFragment_to_updatePasswordFragment)
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

    private fun picture(uri: Uri?) {
        if (uri == null) {
            binding.ivAccount.setImageResource(com.kiri.account.R.drawable.profile)
            binding.iconAdd.visibility = View.VISIBLE
        } else {
            Glide.with(requireContext())
                .load(uri)
                .circleCrop()
                .placeholder(com.kiri.account.R.drawable.profile)
                .into(binding.ivAccount)
            binding.iconAdd.visibility = View.GONE

            val stream = activity?.applicationContext?.contentResolver?.openInputStream(uri)
            val request = stream?.readBytes()?.toRequestBody("image/*".toMediaTypeOrNull())
            val part = request?.let {
                MultipartBody.Part.createFormData(
                    "image", "myPic", it
                )
            }
            lifecycleScope.launchWhenResumed {
                if (part != null) {
                    viewModel.doUploadPhoto(part)
                }
            }
        }
    }

    override fun onUploadPhotoLoading() {
        super.onUploadPhotoLoading()
        shortToast(requireContext(), "Sedang Upload Photo...")
    }

    override fun onUploadPhotoSuccess(data: ProfileData?) {
        super.onUploadPhotoSuccess(data)
        shortToast(requireContext(), "Sukses Upload Photo")
    }

    override fun onUploadPhotoFailed(error: String?) {
        super.onUploadPhotoFailed(error)
        shortToast(requireContext(), getString(R.string.error_message))
    }
}
