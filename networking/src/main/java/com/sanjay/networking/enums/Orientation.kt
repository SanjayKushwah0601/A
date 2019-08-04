package com.sanjay.networking.enums

/**
 * Enum class for orientation options -- Use these when making network calls to the v0 api
 * in order to prevent sending invalid data
 */

enum class Orientation(val orientation: String?) {
    HORIZONTAL("horizontal"),
    VERTICAL("vertical"),
    MIXED("mixed"),
    UNKNOWN(null);

    companion object {
        fun fromString(orientation: String?): Orientation {
            return when (orientation?.toLowerCase()) {
                HORIZONTAL.orientation -> HORIZONTAL
                VERTICAL.orientation -> VERTICAL
                MIXED.orientation -> MIXED
                "portrait" -> VERTICAL
                "landscape" -> HORIZONTAL
                else -> UNKNOWN
            }
        }

        fun fromHeightAndWidth(height: Int, width: Int): Orientation {
            return when {
                height > width -> Orientation.VERTICAL
                width > height -> Orientation.HORIZONTAL
                else -> Orientation.MIXED
            }
        }
    }
}
