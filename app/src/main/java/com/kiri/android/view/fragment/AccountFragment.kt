package com.kiri.android.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.kiri.account.data.models.ProfileData
import com.kiri.account.databinding.AccountFragmentBinding
import com.kiri.account.presentation.viewmodel.AccountResource
import com.kiri.account.presentation.viewmodel.AccountViewModel
import com.kiri.android.R
import com.kiri.android.view.activity.AuthActivity
import com.kiri.common.BuildConfig
import com.kiri.common.data.pref.PrefKey
import com.kiri.common.domain.PrefUseCase
import com.kiri.common.utils.shortToast
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AccountFragment : Fragment(R.layout.account_fragment), View.OnClickListener, AccountResource {
    private val binding by viewBinding<AccountFragmentBinding>()
    private val viewModel: AccountViewModel by viewModel {
        parametersOf(lifecycle, this)
    }
    private val pref: PrefUseCase by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ONCLICK
        binding.apply {
            btnLogout.setOnClickListener(this@AccountFragment)
            cvAccount.setOnClickListener(this@AccountFragment)
            cvFeedback.setOnClickListener(this@AccountFragment)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnLogout -> {
                viewModel.doLogout()
            }
            R.id.cvAccount -> {
                findNavController().navigate(R.id.action_account_fragment_to_accountActivity)
            }
            R.id.cvFeedback -> {
                findNavController().navigate(R.id.action_account_fragment_to_feedbackFragment)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun getData() {
        val getPref = pref.accountData
        val data = Gson().fromJson(getPref, ProfileData::class.java)
        binding.apply {
            tvName.text = data?.name
            tvPhone.text = data?.noHp
            tvId.text = data?.id
            Glide.with(requireContext())
                .load("${BuildConfig.BASE_URL}${data.image}")
                .placeholder(R.drawable.profile)
                .circleCrop()
                .into(binding.ivAccount)
        }
    }

    private fun enableAnimLogout() {
        binding.llLogout.visibility = View.GONE
        binding.llAnim.visibility = View.VISIBLE
    }

    private fun disableAnimLogout() {
        binding.llLogout.visibility = View.VISIBLE
        binding.llAnim.visibility = View.GONE
    }

    override fun onLogoutLoading() {
        super.onLogoutLoading()
        enableAnimLogout()
    }

    override fun onLogoutSuccess() {
        super.onLogoutSuccess()
        disableAnimLogout()
        val intent = Intent(
            requireContext(),
            AuthActivity::class.java
        )
        startActivity(intent)
        pref.removeByKey(PrefKey.TOKEN)
        pref.removeByKey(PrefKey.PROFILE)
        activity?.finish()
    }

    override fun onLogoutFailed(error: String?) {
        super.onLogoutFailed(error)
        disableAnimLogout()
        if (error != null) {
            shortToast(requireContext(), error)
        }
    }
}
