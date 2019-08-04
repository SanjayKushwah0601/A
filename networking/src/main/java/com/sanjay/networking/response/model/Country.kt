package com.sanjay.networking.response.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Country(val name: String, val code: String, val isGDPRCountry: Boolean) : Parcelable