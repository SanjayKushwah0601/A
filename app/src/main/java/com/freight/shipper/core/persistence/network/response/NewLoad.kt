package com.freight.shipper.core.persistence.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*


/**
 * @CreatedBy Sanjay Kushwah on 8/12/2019.
 * sanjaykushwah0601@gmail.com
 */

@Parcelize
class NewLoad(
    @SerializedName("address")
    var address: String? = null,
    @SerializedName("city")
    var city: String? = null,
    @SerializedName("company_name")
    var companyName: String? = null,
    @SerializedName("country_id")
    var countryId: String? = null,
    @SerializedName("created_by")
    var createdBy: String? = null,
    @SerializedName("created_date")
    var createdDate: String? = null,
    @SerializedName("delivery_date")
    var deliveryDate: String? = null,
    @SerializedName("dest_address")
    var destAddress: String? = null,
    @SerializedName("dest_city")
    var destCity: String? = null,
    @SerializedName("dest_country")
    var destCountry: String? = null,
    @SerializedName("dest_latitude")
    var destLatitude: String? = null,
    @SerializedName("dest_longitude")
    var destLongitude: String? = null,
    @SerializedName("dest_postcode")
    var destPostcode: String? = null,
    @SerializedName("dest_state")
    var destState: String? = null,
    @SerializedName("distance")
    var distance: String? = null,
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("finder_id")
    var finderId: String? = null,
    @SerializedName("finder_name")
    var finderName: String? = null,
    @SerializedName("first_name")
    var firstName: String? = null,
    @SerializedName("height")
    var height: String? = null,
    @SerializedName("image")
    var image: String? = null,
    @SerializedName("insurance_detail")
    var insuranceDetail: String? = null,
    @SerializedName("is_deleted")
    var isDeleted: String? = null,
    @SerializedName("last_name")
    var lastName: String? = null,
    @SerializedName("length")
    var length: String? = null,
    @SerializedName("load_category_id")
    var loadCategoryId: String? = null,
    @SerializedName("load_detail")
    var loadDetail: String? = null,
    @SerializedName("load_name")
    var loadName: String? = null,
    @SerializedName("load_status_id")
    var loadStatusId: String? = null,
    @SerializedName("load_status_title")
    var loadStatusTitle: String? = null,
    @SerializedName("load_type")
    var loadType: String? = null,
    @SerializedName("load_weight")
    var loadWeight: String? = null,
    @SerializedName("loads_id")
    var loadsId: String? = null,
    @SerializedName("phone")
    var phone: String? = null,
    @SerializedName("pick_address")
    var pickAddress: String? = null,
    @SerializedName("pick_city")
    var pickCity: String? = null,
    @SerializedName("pick_country")
    var pickCountry: String? = null,
    @SerializedName("pick_date")
    var pickDate: String? = null,
    @SerializedName("pick_latitude")
    var pickLatitude: String? = null,
    @SerializedName("pick_longitude")
    var pickLongitude: String? = null,
    @SerializedName("pick_postcode")
    var pickPostcode: String? = null,
    @SerializedName("pick_state")
    var pickState: String? = null,
    @SerializedName("pick_time")
    var pickTime: String? = null,
    @SerializedName("postcode")
    var postcode: String? = null,
    @SerializedName("price")
    var price: String? = null,
    @SerializedName("quantity")
    var quantity: String? = null,
    @SerializedName("receiver_email")
    var receiverEmail: String? = null,
    @SerializedName("receiver_name")
    var receiverName: String? = null,
    @SerializedName("receiver_phone")
    var receiverPhone: String? = null,
    @SerializedName("refrence_no")
    var refrenceNo: String? = null,
    @SerializedName("registration_no")
    var registrationNo: String? = null,
    @SerializedName("shipper_id")
    var shipperId: String? = null,
    @SerializedName("shipper_name")
    var shipperName: String? = null,
    @SerializedName("shipper_phone")
    var shipperPhone: String? = null,
    @SerializedName("state")
    var state: String? = null,
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("width")
    var width: String? = null
) : Parcelable {
    //    val customerImage: String = "http://www.gravatar.com/avatar/?d=identicon"
//    val pickCity: String = "Pickup City"
//    val destinationCity: String = "Destination City"
//    val pickAddress: String = "Pickup Address"
//    val destinationAddress: String = "Destination Address"
//    val pickTime: String = "10:00 AM, 22 Jul"
//    val destinationTime: String = "10:00 AM, 23 Jul"
//    val amount: String = "120.00"
//    val goodsName: String = "Electronics"
    val paidBy: String = "Receiver"
    val timeLeftToPickup: String = "00:57 Hrs"

    fun getFormattedPickTime(): String {
        val parser = SimpleDateFormat("hh:mm:ss", Locale.getDefault())
        val formatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val output = formatter.format(parser.parse(pickTime))
        return output ?: pickTime ?: "00:00"
    }

    fun getFormattedPickDate(): String {
        val parser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formatter = SimpleDateFormat("dd MMM", Locale.getDefault())
        val output = formatter.format(parser.parse(pickDate))
        return output ?: pickDate ?: "Nil"
    }

    fun getFormattedDestDate(): String {
        val parser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formatter = SimpleDateFormat("dd MMM", Locale.getDefault())
        val output = formatter.format(parser.parse(deliveryDate))
        return output ?: deliveryDate ?: "Nil"
    }
}