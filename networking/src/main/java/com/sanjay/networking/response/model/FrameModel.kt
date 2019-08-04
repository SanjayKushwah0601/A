package com.sanjay.networking.response.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FrameModel(
        @SerializedName("totalSpace")
        val totalSpace: Long?
) : Parcelable