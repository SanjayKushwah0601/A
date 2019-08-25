package com.freight.shipper.model

import com.google.gson.annotations.SerializedName

class MasterConfig(
    @SerializedName("country")
    val country: List<Country>,
    @SerializedName("load_category")
    val loadCategory: List<LoadCategory>,
    @SerializedName("load_status")
    val loadStatus: List<LoadStatus>,
    @SerializedName("state")
    val state: List<State>,
    @SerializedName("vehicle_type")
    val vehicleType: List<VehicleType>,
    @SerializedName("load_type")
    val loadType: List<String>,
    @SerializedName("document_type")
    val documentType: List<String>
)
