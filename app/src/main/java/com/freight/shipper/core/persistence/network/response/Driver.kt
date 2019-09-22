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
class Driver(
    @SerializedName("shipper_id")
    val shipperId: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("bank_acc_no")
    val bankAccNo: String,
    @SerializedName("bank_address")
    val bankAddress: String,
    @SerializedName("bank_name")
    val bankName: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("company_name")
    val companyName: String,
    @SerializedName("country_id")
    val countryId: String,
    @SerializedName("created_by")
    val createdBy: String,
    @SerializedName("created_date")
    val createdDate: String,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("customer_group_id")
    val customerGroupId: String,
    @SerializedName("document")
    val document: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("is_deleted")
    val isDeleted: String,
    @SerializedName("is_driver")
    val isDriver: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("postcode")
    val postcode: String,
    @SerializedName("registration_no")
    val registrationNo: String,
    @SerializedName("shipper_company_id")
    val shipperCompanyId: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("total_driver")
    val totalDriver: String,
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("vehicle_id")
    val vehicleId: String,
    @SerializedName("vehicle_name")
    val vehicleName: String,
    @SerializedName("wire_transfer_num")
    val wireTransferNum: String
) : Parcelable {

    fun getDisplayVehicleStatus(): String {
        return when (status) {
            0 -> StringUtil.getString(R.string.inactive)
            1 -> StringUtil.getString(R.string.active)
            else -> StringUtil.getString(R.string.inactive)
        }
    }
}