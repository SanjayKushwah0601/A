package com.freight.shipper.core.persistence.network.annotations

import androidx.annotation.StringDef

/**
 * Annotation class to constrain an orientation to the given options
 *
 * A String field annotated with @OrientationString will give a Lint warning if something other
 * than these fields is passed into it
 */

@StringDef("horizontal", "vertical")
annotation class OrientationString