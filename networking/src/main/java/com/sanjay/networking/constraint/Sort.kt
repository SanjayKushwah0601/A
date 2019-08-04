package com.sanjay.networking.constraint

import androidx.annotation.StringDef

const val SORT_TYPE_NAME = "name"
const val SORT_TYPE_TIME_PERIOD = "time_period"
const val SORT_TYPE_DATE_ADDED = "date_added"
const val SORT_TYPE_ORDER = "order"

const val SORT_VALUE_ASCENDING = "asc"
const val SORT_VALUE_DESCENDING = "dsc"

@StringDef(SORT_TYPE_NAME, SORT_TYPE_DATE_ADDED, SORT_TYPE_TIME_PERIOD, SORT_TYPE_ORDER)
annotation class SortTypeString

@StringDef(SORT_VALUE_ASCENDING, SORT_VALUE_DESCENDING)
annotation class SortOrderString

/**
 * A class that represents a Sort argument for classes that support it. Not all models support all
 * Sort options, so it might be useful to subclass this to constrain the values for each endpoint
 *
 * Allowed types are defined in the annotation class SortTypeString
 *
 * Allowed values are defined in the annotation class SortOrderString
 */

open class Sort(@SortTypeString type: String, @SortOrderString value: String) : SortFilter(type, value)