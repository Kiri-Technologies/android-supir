package com.kiri.ui

import android.content.Context
import android.content.res.Resources
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import com.kiri.ui.databinding.LayoutDialogBinding

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

    val builder = AlertDialog.Builder(this)
    val binding = LayoutDialogBinding.inflate(LayoutInflater.from(this))
    builder.setView(binding.root)

    if (title == null) {
        binding.tvTitle.visibility = View.GONE
    }

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
