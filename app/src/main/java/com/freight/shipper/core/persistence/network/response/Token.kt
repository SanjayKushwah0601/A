package com.freight.shipper.core.persistence.network.response

import com.google.gson.annotations.SerializedName

/**
 * JSON representation of a device token response
 */

class Token(
        @SerializedName("token")
        val token: String
)