package com.sanjay.networking.annotations

import androidx.annotation.StringDef

/**
 * Annotation class to constrain a day name to the given options
 *
 * A String field annotated with @DayNameString will give a Lint warning if something other
 * than these fields is passed into it
 */

@StringDef("SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY")
annotation class DayNameString