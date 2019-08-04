package com.freight.shipper.utils

import androidx.annotation.StringRes
import com.freight.shipper.FreightApplication

object StringUtil {
    @JvmStatic
    fun getString(@StringRes messageId: Int): String = FreightApplication.instance.getString(messageId)
        ?: ""
}