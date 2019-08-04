package com.sanjay.networking.annotations

import androidx.annotation.StringDef

/**
 * Annotation class to constrain a fill mode string to the given options
 *
 * A String field annotated with @FillModeString will give a Lint warning if something other
 * than these fields is passed into it
 */

@StringDef("as is", "contain", "autocrop", "stretch")
annotation class FillModeString