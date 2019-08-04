package com.sanjay.networking.response.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WifiNetwork(
        @SerializedName("index")
        val index: String?,

        @SerializedName("name")
        val ssid: String?,

        @SerializedName("register")
        val register: Boolean?,

        @SerializedName("connect")
        val connect: Boolean?,

        @SerializedName("delete")
        val delete: Boolean?,

        @SerializedName("security")
        val security: String?,

        @SerializedName("status")
        val status: Boolean?,

        @SerializedName("signal")
        val signalStrength: String?
) : Serializable {
    companion object {
        val CUSTOM = WifiNetwork(null, null, null, null, null, null, null, "-1")
    }

    fun getSignalStrength(): Int? {
        return signalStrength?.toIntOrNull()
    }
}


