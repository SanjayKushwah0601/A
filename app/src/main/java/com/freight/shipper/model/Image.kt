package com.freight.shipper.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * JSON representation of an Image (a.k.a., "item") response
 */
@Parcelize
data class Image(
    val uri: Uri
) : Parcelable {
}