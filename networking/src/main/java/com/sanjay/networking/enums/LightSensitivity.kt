package com.sanjay.networking.enums

enum class LightSensitivity(val intValue: Int?) {
    LOW(4),
    MEDIUM(20),
    HIGH(100),
    UNKNOWN(null);

    companion object {
        fun fromInt(value: Int?): LightSensitivity {
            return when {
                value == null -> UNKNOWN
                value >= HIGH.intValue ?: 100 -> HIGH
                value >= MEDIUM.intValue ?: 20 -> MEDIUM
                value >= LOW.intValue ?: 4 -> LOW
                else -> UNKNOWN
            }
        }
    }
}