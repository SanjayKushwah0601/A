package com.sanjay.networking.response.model

import com.google.gson.annotations.SerializedName
import com.sanjay.networking.annotations.ModelTypeString

/**
 * JSON representation of a Favorite response
 */

class Favorite(
        @SerializedName("id")
        val id: Long,

        @ModelTypeString
        @SerializedName("model")
        val model: String
)