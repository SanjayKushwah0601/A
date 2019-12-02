package com.freight.shipper.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


/**
 * @CreatedBy Sanjay Kushwah on 12/1/2019.
 * sanjaykushwah0601@gmail.com
 */
@Parcelize
class LoadFilter : Parcelable {
    var distance: Int? = null
    var weightFrom: Int? = null
    var weightTo: Int? = null
}