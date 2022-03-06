package com.kiri.account.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kiri.account.R
import com.kiri.account.databinding.AccountFragmentBinding
import com.kiri.account.presentation.viewmodel.AccountResource
import com.kiri.account.presentation.viewmodel.AccountViewModel
import com.kiri.account.presentation.viewmodel.SharedViewModelProfile
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
    private val sharedVM by activityViewModels<SharedViewModelProfile>()
    private val pref: PrefUseCase by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogout.setOnClickListener(this)
        getData()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnLogout -> {
                viewModel.doLogout()
            }
        }
    }

    private fun getData() {
        sharedVM.getData().observe(viewLifecycleOwner) { data ->
            binding.apply {
                tvName.text = data?.name
                tvPhone.text = data?.noHp
                tvId.text = data?.id
            }
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
            Class.forName("com.kiri.android.view.activity.AuthActivity")
        )
        startActivity(intent)
        pref.removeByKey(PrefKey.TOKEN)
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
