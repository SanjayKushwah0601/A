package com.freight.shipper.core.persistence.network.response

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "country")
@Parcelize
class Country(
    @PrimaryKey
    @SerializedName("country_id")
    val countryId: String,
    @SerializedName("country_name")
    val countryName: String,
    @SerializedName("short_code")
    val shortCode: String,
    @SerializedName("status")
    val status: String
) : Parcelable {

}
