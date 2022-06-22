package com.kiri.ui

import android.content.Context
import android.content.res.Resources
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kiri.ui.databinding.LayoutDialogBinding
import com.kiri.ui.databinding.LayoutDialogListBinding

fun disableBtn(view: ButtonView) {
    view.isEnabled = false
    view.setLoading(true)
}

fun enableBtn(view: ButtonView) {
    view.isEnabled = true
    view.setLoading(false)
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun Context.selectedBtn(btn: Button) {
    val theme: Resources.Theme = ContextThemeWrapper(
        this,
        R.style.Theme_Kiri
    ).theme
    btn.setBackgroundColor(
        ResourcesCompat.getColor(
            resources,
            R.color.blue_color,
            theme
        )
    )
    btn.setTextColor(
        ResourcesCompat.getColor(
            resources,
            R.color.white,
            theme
        )
    )
}

fun Context.unSelectedBtn(btn: Button) {
    val theme: Resources.Theme = ContextThemeWrapper(
        this,
        R.style.Theme_Kiri
    ).theme
    btn.setBackgroundColor(
        ResourcesCompat.getColor(
            resources,
            R.color.white,
            theme
        )
    )
    btn.setTextColor(
        ResourcesCompat.getColor(
            resources,
            R.color.black,
            theme
        )
    )
}

fun Context.showDialog(
    title: String? = null,
    message: String?,
    positiveAction: (() -> Unit),
    negativeAction: (() -> Unit)? = null
) {

    var alertDialog: AlertDialog? = null

    val builder = AlertDialog.Builder(this, R.style.RoundedCornersDialog)
    val binding = LayoutDialogBinding.inflate(LayoutInflater.from(this))
    builder.setView(binding.root)

    if (title == null) binding.tvTitle.gone()

    title?.let { _ ->
        binding.tvTitle.text = title
    }

    binding.tvMessage.text = message

    binding.btnPositive.setOnClickListener {
        positiveAction.invoke()
        alertDialog?.dismiss()
    }

    negativeAction?.let {
        binding.btnNegative.setOnClickListener {
            negativeAction.invoke()
            alertDialog?.dismiss()
        }
    }

    builder.create()
    alertDialog = builder.show()
}

fun Context.showDialogList(
    adapter: Any,
    title: String,
    redColor: Boolean? = null
) {

    var alertDialog: AlertDialog? = null
    val builder = AlertDialog.Builder(this)
    val binding = LayoutDialogListBinding.inflate(LayoutInflater.from(this))
    builder.setView(binding.root)
    binding.rvContent.adapter = adapter as BaseQuickAdapter<*, *>
    if (redColor == true) binding.tvTitle.setTextColor(getColor(R.color.red_color))

    binding.tvTitle.text = title
    binding.ivClose.setOnClickListener {
        alertDialog?.dismiss()
    }
    builder.create()
    alertDialog = builder.show()
}
