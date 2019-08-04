package com.sanjay.networking.util

import androidx.annotation.StringRes
import com.sanjay.networking.NetworkingApplication

object StringUtil {
    @JvmStatic
    fun getString(@StringRes messageId: Int): String = NetworkingApplication.context?.getString(messageId)
            ?: ""
}