package com.kiri.account.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.gson.Gson
import com.kiri.account.R
import com.kiri.account.data.models.ProfileData
import com.kiri.account.databinding.DetailAccountFragmentBinding
import com.kiri.common.domain.PrefUseCase
import org.koin.android.ext.android.inject

class DetailAccountFragment : Fragment(R.layout.detail_account_fragment) {

    private val binding by viewBinding<DetailAccountFragmentBinding>()
    private val pref by inject<PrefUseCase>()

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
            val data = Gson().fromJson(pref.accountData, ProfileData::class.java)
            tvId.text = data.id
            tvName.text = data.name
            tvBirthdate.text = data.birthdate
            tvEmail.text = data.email
            tvPhone.text = data.noHp
        }
    }
}
