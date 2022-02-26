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

class OnBoardingActivity : AppCompatActivity(R.layout.activity_onboarding), View.OnClickListener {

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
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.apply {
            setNavigationIcon(R.drawable.back_onboarding)
            navigationIcon?.setTint(resources.getColor(R.color.black))
        }
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
                        1 ->
                            OnBoardingFragment.newInstance(1)
                        else ->
                            OnBoardingFragment.newInstance(2)
                    }
                }

                override fun getItemCount() = 3
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
                            binding.tvTitle.text = resources.getText(R.string.onboarding1_title)
                        }
                        1 -> {
                            isLastValue.set(false)
                            isFirstValue.set(false)
                            binding.tvTitle.text = resources.getText(R.string.onboarding2_title)
                        }
                        2 -> {
                            isLastValue.set(true)
                            isFirstValue.set(false)
                            binding.tvTitle.text = resources.getText(R.string.onboarding3_title)
                        }
                    }
                }
            })
    }

    override fun onClick(view: View?): Unit = with(binding) {
        when (view) {
            buttonNext -> viewPager.currentItem++
            buttonSkip -> viewPager.currentItem = 2
            btnLogin -> {
                startActivity(Intent(this@OnBoardingActivity, AuthActivity::class.java))
                finish()
            }
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
}
