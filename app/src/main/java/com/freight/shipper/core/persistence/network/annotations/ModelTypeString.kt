package com.freight.shipper.core.persistence.network.annotations

import androidx.annotation.StringDef

/**
 * Annotation class to constrain a model type to the given options
 *
 * A String field annotated with @ModelTypeString will give a Lint warning if something other
 * than these fields is passed into it
 */

@StringDef("Item", "Gallery", "Channel", "Artist")
annotation class ModelTypeString