package com.freight.shipper.core.persistence.network.request

import android.content.res.Resources
import androidx.core.os.ConfigurationCompat
import com.google.gson.annotations.SerializedName

data class FrameConnectData(@SerializedName("parameter") val ssid: String,
                            @SerializedName("parameter2") val password: String?,
                            @SerializedName("user_id") val userId: Long,
                            @SerializedName("country") val countryCode: String = getCountryCode(),
                            @SerializedName("epoch") val epochTimeMilliString: String = getEpoch())

private fun getCountryCode(): String {
    return ConfigurationCompat.getLocales(Resources.getSystem().configuration).get(0).country
}

private fun getEpoch(): String {
    return (System.currentTimeMillis() / 1000L).toString()
}