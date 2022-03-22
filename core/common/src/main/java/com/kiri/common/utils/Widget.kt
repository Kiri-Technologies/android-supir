package com.kiri.common.utils

import android.content.Context
import android.widget.Toast

fun shortToast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}
