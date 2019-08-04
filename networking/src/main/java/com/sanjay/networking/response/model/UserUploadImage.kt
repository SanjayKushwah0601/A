package com.sanjay.networking.response.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "upload_image")
@Parcelize
data class UserUploadImage(
        @PrimaryKey
        @SerializedName("imageId") var imageId: Long = -1)
    : Parcelable {
}