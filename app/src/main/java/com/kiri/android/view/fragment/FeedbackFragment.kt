package com.kiri.android.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.gson.Gson
import com.hsalf.smileyrating.SmileyRating
import com.kiri.account.data.models.FeedbackAppData
import com.kiri.account.data.models.ProfileData
import com.kiri.account.databinding.FeedbackFragmentBinding
import com.kiri.account.presentation.viewmodel.AccountResource
import com.kiri.account.presentation.viewmodel.AccountViewModel
import com.kiri.android.R
import com.kiri.common.domain.PrefUseCase
import com.kiri.common.utils.shortToast
import com.kiri.ui.disableBtn
import com.kiri.ui.enableBtn
import com.kiri.ui.gone
import com.kiri.ui.visible
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FeedbackFragment :
    Fragment(R.layout.feedback_fragment),
    View.OnClickListener,
    AccountResource {
    private val binding by viewBinding<FeedbackFragmentBinding>()
    private val viewModel by viewModel<AccountViewModel> {
        parametersOf(lifecycle, this)
    }
    private val pref by inject<PrefUseCase>()
    private lateinit var profileData: ProfileData
    private var review = MutableStateFlow("")
    private var comment = MutableStateFlow("")
    private val isValid = combine(
        review,
        comment
    ) { review, comment ->
        val emptyReview = review.isNotEmpty()
        val emptyComment = comment.isNotEmpty()

        emptyReview and emptyComment
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
        initData()
        initUI()
        initAction()
    }

    private fun initData() {
        profileData = Gson().fromJson(pref.accountData, ProfileData::class.java)
    }

    private fun initUI() {
        ratingButton()
//        binding.rbFeedback.setSmileySelectedListener {
//            review.value = it.name
//        }
        binding.etComment.doOnTextChanged { text, _, _, _ ->
            comment.value = text.toString()
        }
    }

    private fun ratingButton() {
        val rating = listOf(
            SmileyRating.Type.TERRIBLE,
            SmileyRating.Type.BAD,
            SmileyRating.Type.OKAY,
            SmileyRating.Type.GOOD,
            SmileyRating.Type.GREAT
        )
        val title = listOf(
            "awful",
            "sad",
            "neutral",
            "happy",
            "excellent"
        )
        rating.forEach {
            binding.rbFeedback.setFaceBackgroundColor(
                it,
                ContextCompat.getColor(requireContext(), R.color.color_primary)
            )
            binding.rbFeedback.setFaceColor(
                it,
                ContextCompat.getColor(requireContext(), R.color.white)
            )
        }
        binding.rbFeedback.apply {
            setTitle(rating[0], title[0].uppercase())
            setTitle(rating[1], title[1].uppercase())
            setTitle(rating[2], title[2].uppercase())
            setTitle(rating[3], title[3].uppercase())
            setTitle(rating[4], title[4].uppercase())
        }
        binding.rbFeedback.setSmileySelectedListener {
            when (it) {
                rating[0] -> review.value = title[0]
                rating[1] -> review.value = title[1]
                rating[2] -> review.value = title[2]
                rating[3] -> review.value = title[3]
                rating[4] -> review.value = title[4]
                else -> {}
            }
        }
    }

    private fun initAction() = with(binding) {
        btnSubmit.setOnClickListener(this@FeedbackFragment)
        layoutSuccess.apply {
            btnGoHome.setOnClickListener(this@FeedbackFragment)
            tvBack.setOnClickListener(this@FeedbackFragment)
        }
        lifecycleScope.launch {
            isValid.collect {
                btnSubmit.isEnabled = it
            }
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnSubmit -> {
                submit()
            }
            binding.layoutSuccess.btnGoHome -> {
                findNavController().popBackStack()
            }
            binding.layoutSuccess.tvBack -> {
                binding.cvFeedback.visible()
                binding.btnSubmit.visible()
            }
        }
    }

    private fun submit() {
        profileData.id?.let {
            viewModel.feedbackApp(
                it,
                review.value,
                comment.value
            )
        }
    }

    override fun onFeedbackAppLoading() {
        super.onFeedbackAppLoading()
        disableBtn(binding.btnSubmit)
    }

    override fun onFeedbackAppSuccess(data: FeedbackAppData?) {
        super.onFeedbackAppSuccess(data)
        enableBtn(binding.btnSubmit)
        data?.status?.let { shortToast(requireContext(), it) }
        binding.cvFeedback.gone()
        binding.etComment.setText("")
        binding.rbFeedback.resetSmiley()
        binding.btnSubmit.gone()

        binding.layoutSuccess.apply {
            tvName.text = profileData.name
        }
    }

    override fun onFeedbackAppFailed(error: String?) {
        super.onFeedbackAppFailed(error)
        enableBtn(binding.btnSubmit)
        shortToast(requireContext(), getString(R.string.error_message))
    }
}
