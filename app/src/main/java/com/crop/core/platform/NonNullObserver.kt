package com.crop.core.platform

import androidx.lifecycle.Observer

abstract class NonNullObserver<T> : Observer<T> {

    abstract fun onNonNullChanged(value: T)

    override fun onChanged(value: T?) {
        value?.let { onNonNullChanged(it) }
    }
}