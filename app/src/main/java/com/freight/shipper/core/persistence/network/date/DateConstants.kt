package com.freight.shipper.core.persistence.network.date

import java.text.SimpleDateFormat
import java.util.*

interface DateConstants {
    companion object {
        val ISO_8601_DATETIME_FORMAT_WITH_TIMEZONE_OFFSET =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US)
        val SETTINGS_DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val DATE_MONTH_DAY_YEAR_FORMAT = SimpleDateFormat("MMMM dd, yyyy", Locale.US)

        fun getDateTimeFormat() = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    }
}