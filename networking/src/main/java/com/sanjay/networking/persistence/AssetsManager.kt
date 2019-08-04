package com.sanjay.networking.persistence

import android.content.Context
import com.google.gson.Gson
import com.sanjay.networking.response.model.Country
import java.io.FileNotFoundException

class AssetsManager(val context: Context) {

    fun fetchCountryList(): MutableList<Country> {
        return try {
            val countryCodes: String = context.assets.open("country_codes.json")
                    .bufferedReader().use { it.readText() }
            Gson().fromJson(countryCodes, Array<Country>::class.java).toMutableList()
        } catch (exception: FileNotFoundException) {
            mutableListOf<Country>()
        }
    }

}