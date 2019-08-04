package com.sanjay.networking.response.model

import android.os.Parcelable
import com.sanjay.networking.enums.FillMode
import com.sanjay.networking.enums.LetterboxColor
import com.sanjay.networking.enums.LightSensitivity
import com.sanjay.networking.enums.Orientation
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * Model to represent options used to update a Canvas with the API. Fields are all nullable
 * and by default, will simply not be passed if left null, so if you explicitly don't want to update
 * a field just leave it null in the object when making the call with Retrofit
 */
@Parcelize
class DeviceOptions(
        var alias: String? = null,
        private var orientation: String? = null, //"horizontal", "vertical"
        var orientationMatch: Boolean? = null,
        var alsEnabled: Boolean? = null,
        private var alsSensitivity: Int? = null, //0-100
        var goesDark: Boolean? = null,
        var imageShuffle: Boolean? = null,
        var imageDuration: Long? = null, //0-infinity
        var previewDuration: Long? = null, //1-180
        var overlayDuration: Long? = null, //1-180
        var gestureFeedback: Boolean? = null,
        var timezone: String? = null, //GET /device/timezones for list
        private var backgroundColor: String? = null, //grey, white, black
        private var fillMode: String? = null, //as is, contain, autocrop, stretch
        var galleryRotation: Boolean? = null,
        var displayLanguage: String? = null // Canvas language

) : Parcelable {
    constructor(device: Device): this(
            device.alias,
            device.getOrientation().orientation,
            device.orientationMatch,
            device.lightSensorEnabled,
            device.getLightSensitivity().intValue,
            device.goesDark,
            device.imageShuffle,
            device.imageDuration,
            device.previewDuration,
            device.overlayDuration,
            device.gestureFeedback,
            device.timezone,
            device.getLetterboxColor().color,
            device.getFillMode().value,
            device.galleryRotation,
            device.displayLanguage
    )

    val localizedDisplayLanguage: String get() {
        val displayLocale: Locale = displayLanguage?.let { Locale(it) } ?: Locale.US
        return displayLocale.getDisplayName(Locale.getDefault())
    }

    fun getFillMode(): FillMode {
        return FillMode.fromString(fillMode)
    }

    fun getOrientation(): Orientation {
        return Orientation.fromString(orientation)
    }

    fun getLetterboxColor(): LetterboxColor {
        return LetterboxColor.fromString(backgroundColor)
    }

    fun getLightSensitivity(): LightSensitivity {
        return LightSensitivity.fromInt(alsSensitivity)
    }
}