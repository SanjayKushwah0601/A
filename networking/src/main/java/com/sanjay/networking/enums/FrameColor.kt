package com.sanjay.networking.enums

/**
 * Enum class for frame color options -- Use these when making network calls to the v0 api
 * in order to prevent sending invalid data
 */

enum class FrameColor(val color: String) {
    LIGHT("light"),
    WHITE("white"),
    BLACK("black"),
    WINSLOW("winslow")
}
