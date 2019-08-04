package com.sanjay.networking.response.model

import android.os.Parcelable
import androidx.annotation.IntRange
import com.google.gson.annotations.SerializedName
import com.sanjay.networking.annotations.BackgroundColorString
import com.sanjay.networking.annotations.FillModeString
import com.sanjay.networking.annotations.FrameColorString
import com.sanjay.networking.annotations.OrientationString
import com.sanjay.networking.date.DateConstants
import com.sanjay.networking.enums.FillMode
import com.sanjay.networking.enums.LetterboxColor
import com.sanjay.networking.enums.LightSensitivity
import com.sanjay.networking.enums.Orientation
import kotlinx.android.parcel.Parcelize
import timber.log.Timber
import java.util.*

/**
 * JSON representation of a Device response
 */
@Parcelize
data class Device(
        @SerializedName("id")
        var id: Long,

        @SerializedName("alias")
        var alias: String,

        @SerializedName("alsEnabled")
        var lightSensorEnabled: Boolean?,

        @IntRange(from = 0, to = 100)
        @SerializedName("alsSensitivity")
        private var lightSensorSensitivity: Int?,

        @SerializedName("goesDark")
        var goesDark: Boolean?,

        @BackgroundColorString
        @SerializedName("backgroundColor")
        private var backgroundColor: String?,

        @FrameColorString
        @SerializedName("frameColor")
        var frameColor: String?,

        @SerializedName("gestureFeedback")
        var gestureFeedback: Boolean?,

        @SerializedName("gestureFeedbackHelp")
        var gestureFeedbackHelp: Boolean?,

        @FillModeString
        @SerializedName("fillMode")
        private var fillMode: String?,

        @SerializedName("orientationMatch")
        var orientationMatch: Boolean?,

        @IntRange(from = 0, to = Long.MAX_VALUE)
        @SerializedName("imageDuration")
        var imageDuration: Long?,

        @OrientationString
        @SerializedName("orientation")
        private var orientation: String?,

        @IntRange(from = 1, to = 180)
        @SerializedName("overlayDuration")
        var overlayDuration: Long?,

        @IntRange(from = 1, to = 180)
        @SerializedName("previewDuration")
        var previewDuration: Long?,

        @SerializedName("galleryRotation")
        var galleryRotation: Boolean?,

        @SerializedName("timezone")
        var timezone: String?,

        @SerializedName("imageShuffle")
        var imageShuffle: Boolean?,

        @SerializedName("currentImage")
        var currentImage: String?,

        @SerializedName("freeSpace")
        var freeSpace: Long?,

        @SerializedName("galleryIds")
        var galleryIds: List<Long>?,

        @SerializedName("localIp")
        var localIp: String?,

        @SerializedName("productKey")
        var productKey: String?,

        @SerializedName("status")
        var status: String?,

        @SerializedName("syncedAt")
        var syncedAt: String?,

        @SerializedName("model")
        var model: Long?,

        @SerializedName("user")
        var user: String?,

        @SerializedName("userId")
        var userId: Long?,

        @SerializedName("version")
        var version: String?,

        @SerializedName("schedulerEnabled")
        var schedulerEnabled: Boolean?,

        @SerializedName("frameImageUrl")
        var frameImageUrl: String?,

        @SerializedName("frameModel")
        var frameModel: FrameModel?,

        @SerializedName("currentGallery")
        var currentGallery: Long?,

        @SerializedName("displayLanguage")
        var displayLanguage: String

) : Parcelable {

    val isReachable: Boolean get() = status.equals("online") || status.equals("idle")
    val cleanedIp: String? get() = localIp?.replace("'".toRegex(), "")

    fun getSyncedDate(): Date {
        return try {
            DateConstants.ISO_8601_DATETIME_FORMAT_WITH_TIMEZONE_OFFSET.parse(syncedAt)
        } catch (parseException: Exception) {
            Date(0)
        }
    }

    fun getFillMode(): FillMode {
        return FillMode.fromString(fillMode)
    }

    fun getOrientation(): Orientation {
        return Orientation.fromString(orientation)
    }

    fun setOrientation(orientation: Orientation) {
        this.orientation = orientation.orientation
    }

    fun getLetterboxColor(): LetterboxColor {
        return LetterboxColor.fromString(backgroundColor)
    }

    fun getLightSensitivity(): LightSensitivity {
        return LightSensitivity.fromInt(lightSensorSensitivity)
    }

    fun getTotalSpaceGb(): Double {
        val totalSpaceMb = frameModel?.totalSpace?.toDouble() ?: 0.toDouble()
        return totalSpaceMb / 1024
    }

    fun getFreeSpaceGb(): Double {
        val freeSpaceMb = freeSpace?.toDouble() ?: 0.toDouble()
        return freeSpaceMb / 1024
    }

    fun willDisplay(gallery: Gallery?): Boolean {
        if (gallery == null) {
            return false
        }

        return if (orientationMatch == null || orientationMatch == false || gallery.orientationType == Orientation.MIXED) {
            true
        } else {
            getOrientation() == gallery.orientationType
        }
    }

    // this method use to compare orientation of image and canvas
    fun willDisplay(image: Image?): Boolean {
        if (image == null) {
            return false
        }

        return if (orientationMatch == null || orientationMatch == false || image.getOrientation() == Orientation.MIXED) {
            true
        } else {
            getOrientation() == image.getOrientation()
        }
    }
}