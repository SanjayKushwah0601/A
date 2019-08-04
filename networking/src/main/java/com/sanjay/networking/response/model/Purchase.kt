package com.sanjay.networking.response.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Purchase<T : Parcelable>(
        @SerializedName("creditCardBrand")
        val creditCardBrand: String?,
        @SerializedName("creditCardLast4")
        val creditCardLast4: String?,
        @SerializedName("date")
        val date: String?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("object")
        val purchasedArtwork: T,
        @SerializedName("objectId")
        val objectId: Int?,
        @SerializedName("objectType")
        val objectType: String?,
        @SerializedName("price")
        val price: Long,
        @SerializedName("purchaseType")
        val purchaseType: String?,
        @SerializedName("userId")
        val userId: Int?
) : Parcelable