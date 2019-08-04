package com.sanjay.networking.annotations

import androidx.annotation.StringDef

/**
 * Annotation class to constrain a background color to the given options
 *
 * A String field annotated with @BackgroundColorString will give a Lint warning if something other
 * than these fields is passed into it
 */

@StringDef("grey", "white", "black")
annotation class BackgroundColorString