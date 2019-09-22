package com.freight.shipper.core.persistence.network.response

import android.os.Parcelable
import com.freight.shipper.R
import com.freight.shipper.utils.StringUtil
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


/**
 * @CreatedBy Sanjay Kushwah on 9/15/2019.
 * sanjaykushwah0601@gmail.com
 */
@Parcelize
class Vehicle(
    @SerializedName("vehicle_id")
    val vehicleId: String,
    @SerializedName("vehicle_name")
    val vehicleName: String,
    @SerializedName("shipper_id")
    val shipperId: String,
    @SerializedName("shipper_company_id")
    val shipperCompanyId: String,
    @SerializedName("vehicle_type_id")
    val vehicleTypeId: String,
    @SerializedName("load_type")
    val loadType: String,
    @SerializedName("registration_no")
    val regNumber: String,
    @SerializedName("number_plate")
    val numPlate: String,
    @SerializedName("length")
    val length: String,
    @SerializedName("width")
    val width: String,
    @SerializedName("height")
    val height: String,
    @SerializedName("weight")
    val weight: String,
    @SerializedName("photo")
    val photo: String,
    @SerializedName("document")
    val document: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("is_deleted")
    val isDeleted: String,
    @SerializedName("created_date")
    val createdDate: String,
    @SerializedName("vehicle_type")
    val vehicleType: String
) : Parcelable {

    fun getDisplayVehicleStatus(): String {
        return when (status) {
            0 -> StringUtil.getString(R.string.inactive)
            1 -> StringUtil.getString(R.string.active)
            else -> StringUtil.getString(R.string.inactive)
        }
    }
}