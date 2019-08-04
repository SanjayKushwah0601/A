package com.sanjay.networking.enums

/**
 * Enum class for fill mode options -- Use these when making network calls to the v0 api
 * in order to prevent sending invalid data
 */

enum class FillMode(val value: String?) {
    AS_IS("as is"),
    CONTAIN("contain"),
    AUTOCROP("auto crop"),
    STRETCH("stretch"),
    UNKNOWN(null);

    companion object {
        fun fromString(fillMode: String?): FillMode {
            return when (fillMode?.toLowerCase()) {
                AUTOCROP.value -> FillMode.AUTOCROP
                CONTAIN.value -> FillMode.CONTAIN
                STRETCH.value -> FillMode.STRETCH
                AS_IS.value -> FillMode.AS_IS
                else -> FillMode.UNKNOWN
            }
        }
    }
}