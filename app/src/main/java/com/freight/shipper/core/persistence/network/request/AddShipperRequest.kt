package com.freight.shipper.core.persistence.network.request

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


/**
 * @CreatedBy Sanjay Kushwah on 9/11/2019.
 * sanjaykushwah0601@gmail.com
 */
@Parcelize
class AddShipperRequest(
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var phone: String = "",
    var address: String = "",
    var city: String = "",
    var state: String = "",
    var countryId: String = "",
    var postcode: String = "",
    var password: String = ""
) : Parcelable {

    @Throws(IllegalAccessException::class)
    fun isAllFilled(): Boolean {
        for (f in javaClass.declaredFields)
            if (f.get(this) == null || f.get(this).toString().isEmpty())
                return false
        return true
    }
}