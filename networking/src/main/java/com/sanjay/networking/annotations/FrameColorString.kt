package com.sanjay.networking.annotations

import androidx.annotation.StringDef

/**
 * Annotation class to constrain a frame color to the given options
 *
 * A String field annotated with @FrameColorString will give a Lint warning if something other
 * than these fields is passed into it
 */

@StringDef("light", "white", "black", "winslow")
annotation class FrameColorString