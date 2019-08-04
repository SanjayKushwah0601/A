package com.sanjay.networking.enums

/**
 * @author GWL
 */
enum class EventType(val value: String) {
    PLAYLIST("gallery"),
    SURPRISE("dont_care"),
    SLEEP("sleep"),
    WAKE("wake"),
    ANYTHING("anything");

    companion object {
        fun fromString(eventType: String?): EventType {
            return when (eventType?.toLowerCase()) {
                PLAYLIST.value -> EventType.PLAYLIST
                SURPRISE.value -> EventType.SURPRISE
                SLEEP.value -> EventType.SLEEP
                WAKE.value -> EventType.WAKE
                else -> EventType.ANYTHING
            }
        }
    }
}