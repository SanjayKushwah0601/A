package com.freight.shipper.core.persistence.network.response

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "load_category")
@Parcelize
class LoadCategory(
    @PrimaryKey
    @SerializedName("load_category_id")
    val loadCategoryId: String,
    @SerializedName("is_deleted")
    val isDeleted: String,
    @SerializedName("load_category")
    val loadCategory: String,
    @SerializedName("status")
    val status: String
) : Parcelable {
}