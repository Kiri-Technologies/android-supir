package com.kiri.common.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
