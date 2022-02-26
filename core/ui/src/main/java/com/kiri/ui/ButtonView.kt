package com.kiri.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView

class ButtonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private val progressBar: LottieAnimationView
    private val buttonTextView: TextView

    init {
        val root = LayoutInflater.from(context).inflate(R.layout.button_kiri, this, true)
        buttonTextView = root.findViewById(R.id.button_text)
        progressBar = root.findViewById(R.id.progress_indicator)
        loadAttr(attrs, defStyleAttr)
    }

    private fun loadAttr(attrs: AttributeSet?, defStyleAttr: Int) {
        val arr = context.obtainStyledAttributes(
            attrs,
            R.styleable.ButtonView,
            defStyleAttr,
            0
        )

        val buttonText = arr.getString(R.styleable.ButtonView_text)
        val loading = arr.getBoolean(R.styleable.ButtonView_loading, false)
        val enabled = arr.getBoolean(R.styleable.ButtonView_enabled, true)
        val lottieResId = arr.getResourceId(R.styleable.ButtonView_lottie_resId, R.raw.loading_anim)
        arr.recycle()
        isEnabled = enabled
        buttonTextView.isEnabled = enabled
        setText(buttonText)
        progressBar.setAnimation(lottieResId)
        setLoading(loading)
    }

    fun setLoading(loading: Boolean) {
        isClickable = !loading // Disable clickable when loading
        if (loading) {
            buttonTextView.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        } else {
            buttonTextView.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }
    }

    fun setText(text: String?) {
        buttonTextView.text = text
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        buttonTextView.isEnabled = enabled
    }
}
