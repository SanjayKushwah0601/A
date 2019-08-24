package com.freight.shipper.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(
    tableName = "state"/*,
    foreignKeys = [ForeignKey(
        entity = Country::class,
        parentColumns = arrayOf("countryId"),
        childColumns = arrayOf("countryId"),
        onDelete = ForeignKey.CASCADE
    )]*/
)
@Parcelize
class State(
    @PrimaryKey
    @SerializedName("state_id")
    val stateId: String,
    @SerializedName("country_id")
    val countryId: String,
    @SerializedName("status")
    val status: String?,
    @SerializedName("country")
    val country: String,
    @SerializedName("state_name")
    val stateName: String,
    @SerializedName("country_code")
    val countryCode: String,
    @SerializedName("abbr")
    val abbr: String,
    @SerializedName("fips")
    val fips: String,
    @SerializedName("subdivision_en")
    val subdivisionEn: String
) : Parcelable