package com.kiri.account.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kiri.account.R
import com.kiri.account.databinding.AccountFragmentBinding
import com.kiri.account.presentation.viewmodel.AccountResource
import com.kiri.account.presentation.viewmodel.AccountViewModel
import com.kiri.common.data.pref.PrefKey
import com.kiri.common.domain.PrefUseCaseImpl
import com.kiri.common.utils.shortToast
import com.kiri.ui.disableBtn
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AccountFragment : Fragment(R.layout.account_fragment), View.OnClickListener, AccountResource {
    private val binding by viewBinding<AccountFragmentBinding>()
    private val viewModel: AccountViewModel by viewModel {
        parametersOf(lifecycle, this)
    }
    private val pref: PrefUseCaseImpl by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogout.setOnClickListener(this)
        pref.token?.let { shortToast(requireContext(), it) }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnLogout -> {
                viewModel.doLogout()
            }
        }
    }

    override fun onLogoutLoading() {
        disableBtn(binding.btnLogout)
    }

    override fun onLogoutSuccess() {
        val intent = Intent(
            requireContext(),
            Class.forName("com.kiri.android.view.activity.AuthActivity")
        )
        startActivity(intent)
        pref.removeByKey(PrefKey.TOKEN)
        activity?.finish()
    }

    override fun onLogoutFailed(error: String?) {
        if (error != null) {
            shortToast(requireContext(), error)
        }
    }
}
