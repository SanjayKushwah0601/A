package com.sanjay.networking.enums

/**
 * Enum class for background color options -- Use these when making network calls to the v0 api
 * in order to prevent sending invalid data
 */

enum class LetterboxColor(val color: String?) {
    GREY("grey"),
    WHITE("white"),
    BLACK("black"),
    UNKNOWN(null);

    companion object {
        fun fromString(value: String?): LetterboxColor {
            return when (value?.toLowerCase()) {
                GREY.color -> GREY
                WHITE.color -> WHITE
                BLACK.color -> BLACK
                else -> UNKNOWN
            }
        }
    }
}