package com.kiri.android.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableBoolean
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.kiri.android.R
import com.kiri.android.databinding.ActivityOnboardingBinding
import com.kiri.android.view.fragment.OnBoardingFragment
import com.kiri.common.domain.PrefUseCase
import com.kiri.ui.gone
import com.kiri.ui.visible
import org.koin.android.ext.android.inject

class OnBoardingActivity : AppCompatActivity(R.layout.activity_onboarding), View.OnClickListener {

    private val pref: PrefUseCase by inject()
    private val binding by viewBinding(ActivityOnboardingBinding::bind)
    private var mTabLayoutMediator: TabLayoutMediator? = null
    private val isLastValue = ObservableBoolean(false)
    private val isFirstValue = ObservableBoolean(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            isLast = isLastValue
            isFirst = isFirstValue
        }.let {
            it.lifecycleOwner = this
            it.listener = this
        }
//        setSupportActionBar(binding.toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        binding.toolbar.apply {
//            setNavigationIcon(R.drawable.ic_back_arrow)
//            navigationIcon?.setTint(resources.getColor(R.color.black))
//        }
        setupStep()
        setupListener()
    }

    private fun setupStep() {
        binding.viewPager.apply {
            adapter = object : FragmentStateAdapter(supportFragmentManager, lifecycle) {
                override fun createFragment(position: Int): Fragment {
                    return when (position) {
                        0 ->
                            OnBoardingFragment.newInstance(0)
                        else ->
                            OnBoardingFragment.newInstance(1)
                    }
                }

                override fun getItemCount() = 2
            }
            addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
                override fun onViewDetachedFromWindow(p0: View?) {
                    adapter = null
                }

                override fun onViewAttachedToWindow(p0: View?) {}
            })
        }
        mTabLayoutMediator = TabLayoutMediator(
            binding.tabLayout,
            binding.viewPager
        ) { _, _ -> }
        mTabLayoutMediator?.attach()
    }

    private fun setupListener() {
        binding.viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                    when (position) {
                        0 -> {
                            isLastValue.set(false)
                            isFirstValue.set(true)
                            binding.apply {
                                tvTitle.text = resources.getText(R.string.onboarding1_title)
                                tvDesc.text = resources.getText(R.string.onboarding1_desc)
                                btnLogin.gone()
                                btnRegister.gone()
                            }
                        }
                        1 -> {
                            isLastValue.set(true)
                            isFirstValue.set(false)
                            binding.apply {
                                tvDesc.text = resources.getText(R.string.onboarding2_desc)
                                tvTitle.text = resources.getText(R.string.onboarding2_title)
                                btnLogin.visible()
                                btnRegister.visible()
                            }
                        }
                    }
                }
            })
    }

    override fun onClick(view: View?): Unit = with(binding) {
        when (view) {
            buttonNext -> viewPager.currentItem++
            btnLogin -> toLogin()
            btnRegister -> toRegister()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                binding.viewPager.currentItem--
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        mTabLayoutMediator?.detach()
        mTabLayoutMediator = null
        super.onDestroy()
    }

    private fun toLogin() {
        pref.firstStart = false
        startActivity(Intent(this@OnBoardingActivity, AuthActivity::class.java))
        finish()
    }

    private fun toRegister() {
        pref.firstStart = false
        val intent = Intent(this@OnBoardingActivity, AuthActivity::class.java).putExtra(
            AuthActivity.IS_REGISTER,
            true
        )
        startActivity(intent)
        finish()
    }
}
