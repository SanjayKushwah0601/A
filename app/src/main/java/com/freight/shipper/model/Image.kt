package com.freight.shipper.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * JSON representation of an Image (a.k.a., "item") response
 */
@Entity(tableName = "image")
@Parcelize
data class Image(
        @PrimaryKey
        @SerializedName("id") var id: Long = -1,
        @SerializedName("artistId") val artistId: Long?,
        @SerializedName("artistName") val artistName: String?,
        @SerializedName("author") val author: String?)
    : Parcelable {

}