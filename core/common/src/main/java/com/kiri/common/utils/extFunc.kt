package com.kiri.common.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.text.format.Time
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

inline fun <reified V : ViewModel> Lifecycle.register(
    viewModel: V,
    crossinline observer: (V) -> LifecycleObserver
): V {
    CoroutineScope(Dispatchers.Main.immediate).launch {
        repeatOnLifecycle(Lifecycle.State.CREATED) {
            addObserver(observer.invoke(viewModel))
        }
        repeatOnLifecycle(Lifecycle.State.DESTROYED) {
            removeObserver(observer.invoke(viewModel))
        }
    }
    return viewModel
}

// TIME
fun getLocaleIndonesia(): Locale {
    return Locale("id", "ID")
}

fun String.toDate(fromFormat: String, locale: Locale = Locale.getDefault()): Date? {
    return try {
        SimpleDateFormat(fromFormat, locale).parse(this)
    } catch (t: Throwable) {
        null
    }
}

fun String.dateToDateMillis(time: Time? = null): Long {
    val date = this.toDate("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val result = date?.let {

        val utcTime = it.time
        val timeFormat = time ?: Time()
        timeFormat.set(utcTime + TimeZone.getDefault().getOffset(utcTime))
        timeFormat.toMillis(true)
    } ?: 0

    return result
}

fun Long.toInstant(): Instant {
    return Instant.ofEpochMilli(this)
}

fun Long.toLocalDateTime(): LocalDateTime {
    return LocalDateTime.ofInstant(
        toInstant(),
        ZoneId.systemDefault()
    )
}

fun Long.toFormattedString(pattern: String): String {
    return toLocalDateTime().format(
        DateTimeFormatter.ofPattern(pattern).withLocale(getLocaleIndonesia())
    )
}

@SuppressLint("SimpleDateFormat")
fun String.toIndonesiaTime(parserPattern: String, targetFormat: String): String {
    val parser = SimpleDateFormat(parserPattern)
    val formatter = SimpleDateFormat(targetFormat, getLocaleIndonesia())
    return formatter.format(parser.parse(this))
}

// Currency
fun Long.toFormatRupiah(): String {
    val localeID = Locale("in", "ID")
    val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
    return formatRupiah.format(this).substringBeforeLast(",")
}

@RequiresApi(Build.VERSION_CODES.Q)
fun Context.foregroundLocation(activity: Activity, action: (() -> Unit)? = null) {
    val hasForegroundLocationPermission = ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
    if (hasForegroundLocationPermission) {
        val hasBackgroundLocationPermission = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        if (hasBackgroundLocationPermission) {
            // handle location update
            action?.invoke()
        } else {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION), 111
            )
        }
    } else {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ),
            111
        )
    }
}

fun Context.permission(
    permissionResult: ActivityResultLauncher<String>,
    permissionType: String,
    action: (() -> Unit)? = null
) {
    when (PackageManager.PERMISSION_GRANTED) {
        ContextCompat.checkSelfPermission(
            this,
            permissionType
        ) -> {
            action?.invoke()
        }
        else -> {
            permissionResult.launch(
                permissionType
            )
        }
    }
}
