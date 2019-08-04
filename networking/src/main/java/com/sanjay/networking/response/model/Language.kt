package com.sanjay.networking.response.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
class Language(
        @SerializedName("code")
        val code: String,
        @SerializedName("name")
        val name: String
) : Parcelable