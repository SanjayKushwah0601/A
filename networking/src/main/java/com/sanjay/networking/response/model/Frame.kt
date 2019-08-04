package com.sanjay.networking.response.model


import com.google.gson.annotations.SerializedName

class Frame {
    @SerializedName("name")
    var name: String = ""

    @SerializedName("product_key")
    var productKey: String = ""

    @SerializedName("version")
    var version: String = ""

    @SerializedName("features")
    var features: FrameFeatures? = null

    val isSoftApEnabled: Boolean
        get() {
            return features?.softap == true
        }
}

@Deprecated("")
class FrameFeatures {

    @SerializedName("supported_languages")
    var supportedLanguage: List<String>? = null

    var softap: Boolean = false
}

