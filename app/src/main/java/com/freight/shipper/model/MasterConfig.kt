package com.freight.shipper.model

import com.google.gson.annotations.SerializedName

class MasterConfig(
    @SerializedName("country")
    val country: List<Country>,
    @SerializedName("load_category")
    val loadCategory: List<LoadCategory>,
    @SerializedName("load_status")
    val loadStatus: List<LoadStatus>,
    @SerializedName("load_type")
    val loadType: List<String>,
    @SerializedName("state")
    val state: List<State>
)
