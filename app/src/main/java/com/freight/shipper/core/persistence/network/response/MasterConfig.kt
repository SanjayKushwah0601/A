package com.freight.shipper.core.persistence.network.response

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.freight.shipper.core.persistence.db.converters.CustomTypeConverters
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "config")
@TypeConverters(CustomTypeConverters::class)
class MasterConfig(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @SerializedName("country")
    val countries: List<Country>,
    @SerializedName("load_category")
    val loadCategories: List<LoadCategory>,
    @SerializedName("load_status")
    val loadStatusList: List<LoadStatus>,
    @SerializedName("state")
    val stateList: List<State>,
    @SerializedName("vehicle_type")
    val vehicleTypes: List<VehicleType>,
    @SerializedName("load_type")
    val loadTypes: List<String>,
    @SerializedName("document_type")
    val documentTypes: List<String>
) : Parcelable
