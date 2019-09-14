package com.freight.shipper.core.persistence.network.request

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


/**
 * @CreatedBy Sanjay Kushwah on 9/11/2019.
 * sanjaykushwah0601@gmail.com
 */
@Parcelize
class AddVehicleRequest(
    var images: MutableList<String> = mutableListOf(),
    var vehicleName: String? = null,
    var vehicleType: String? = null,
    var regNumber: String? = null,
    var length: String? = null,
    var width: String? = null,
    var height: String? = null,
    var weight: String? = null,
    var numberPlate: String? = null
) : Parcelable {

    constructor(
        vehicleName: String,// = "My Vehicle",
        vehicleType: String?,// = "2",
        regNumber: String?,// = "MP093103",
        length: String?,// = "9",
        width: String?,// = "4",
        height: String?,// = "5",
        weight: String?,// = "120",
        numberPlate: String?// = "MP0931039"
    ) : this(
        mutableListOf(),
        vehicleName,
        vehicleType,
        regNumber,
        length,
        width,
        height,
        weight,
        numberPlate
    )
}