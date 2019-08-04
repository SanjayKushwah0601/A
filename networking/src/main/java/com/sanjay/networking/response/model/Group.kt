package com.sanjay.networking.response.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * JSON representation of a Group response
 */

@Parcelize
class Group(
        @SerializedName("id")
        val id: Long,

        @SerializedName("name")
        val name: String?
) : Parcelable