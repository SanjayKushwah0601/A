package com.freight.shipper.core.persistence.network.constraint

import androidx.annotation.StringDef

const val FILTER_TYPE_NAME = "name"
const val FILTER_TYPE_TIME_PERIOD = "time_period"

@StringDef(FILTER_TYPE_NAME, FILTER_TYPE_TIME_PERIOD)
annotation class FilterTypeString

const val FILTER_VALUE_TIME_PERIOD_RENAISSANCE = "Renaissance"
const val FILTER_VALUE_TIME_PERIOD_1600 = "1600"
const val FILTER_VALUE_TIME_PERIOD_1700 = "1700"
const val FILTER_VALUE_TIME_PERIOD_1800 = "1800"
const val FILTER_VALUE_TIME_PERIOD_1900 = "1900"
const val FILTER_VALUE_TIME_PERIOD_2000 = "2000"

@StringDef(FILTER_VALUE_TIME_PERIOD_RENAISSANCE,
        FILTER_VALUE_TIME_PERIOD_1600,
        FILTER_VALUE_TIME_PERIOD_1700,
        FILTER_VALUE_TIME_PERIOD_1800,
        FILTER_VALUE_TIME_PERIOD_1900,
        FILTER_VALUE_TIME_PERIOD_2000)
annotation class TimePeriodFilterString

/**
 * A class that represents a Filter argument for classes that support it. Not all models support all
 * Filter options, so it might be useful to subclass this to further constrain allowed values
 *
 * Allowed values for Filter Type are defined in the annotation class FilterTypeString
 *
 * Any value is allowed for Values, but this probably should be constrained in certain subclasses
 * when appropriate
 */

open class Filter(@FilterTypeString type: String, value: String) : SortFilter(type, value)