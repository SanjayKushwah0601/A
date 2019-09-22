package com.freight.shipper.core.persistence.network.response

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "load_status")
@Parcelize
class LoadStatus(
    @PrimaryKey
    @SerializedName("load_status_id")
    val loadStatusId: String,
    @SerializedName("cancel_fee")
    val cancelFee: String,
    @SerializedName("color_code")
    val colorCode: String,
    @SerializedName("is_default")
    val isDefault: String,
    @SerializedName("is_deleted")
    val isDeleted: String,
    @SerializedName("load_status_title")
    val loadStatusTitle: String,
    @SerializedName("sequence")
    val sequence: String,
    @SerializedName("status")
    val status: String
) : Parcelable
