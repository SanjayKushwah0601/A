package com.freight.shipper.ui.authentication.signup

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


/**
 * @CreatedBy Sanjay Kushwah on 8/5/2019.
 * sanjaykushwah0601@gmail.com
 */
@Parcelize
data class CompanySignup(
    var firstName: String,
    var lastName: String,
    var email: String,
    var phone: String,
    var address: String,
    var addressLine1: String,
    var addressLine2: String,
    var country: String,
    var state: String,
    var city: String,
    var postcode: String,
    var password: String,
    var companyName: String
) : Parcelable {

    constructor() : this("", "", "", "", "", "", "", "", "", "", "", "", "")
}