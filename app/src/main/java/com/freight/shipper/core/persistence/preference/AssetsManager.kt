package com.freight.shipper.core.persistence.preference

import android.content.Context

class AssetsManager(val context: Context) {

    /*fun fetchCountryList(): MutableList<Country> {
        return try {
            val countryCodes: String = context.assets.open("country_codes.json")
                    .bufferedReader().use { it.readText() }
            Gson().fromJson(countryCodes, Array<Country>::class.java).toMutableList()
        } catch (exception: FileNotFoundException) {
            mutableListOf<Country>()
        }
    }*/

}