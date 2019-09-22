package com.freight.shipper.core.persistence.network.response

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "vehicle_type")
@Parcelize
class VehicleType(
    @PrimaryKey
    @SerializedName("vehicle_type_id")
    val vehicleTypeId: String,
    @SerializedName("height")
    val height: String,
    @SerializedName("is_deleted")
    val isDeleted: String,
    @SerializedName("length")
    val length: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("vehicle_type")
    val vehicleType: String,
    @SerializedName("weight")
    val weight: String,
    @SerializedName("width")
    val width: String
) : Parcelable
