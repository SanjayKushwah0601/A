package com.sanjay.networking.response.model

import com.google.gson.annotations.SerializedName

/**
 * JSON representation of a device token response
 */

class Token(
        @SerializedName("token")
        val token: String
)