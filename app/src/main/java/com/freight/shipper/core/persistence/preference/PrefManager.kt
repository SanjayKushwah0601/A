package com.freight.shipper.core.persistence.preference

import android.content.Context
import android.content.SharedPreferences

/**
 * @author Sanjay Kushwah
 */
class PrefManager(context: Context) {

    companion object {
        private const val SHARED_PREFS_NAME = "FREIGHT_SHARED_PREFS"
    }

    private val sharedPreferences: SharedPreferences

    init {
        this.sharedPreferences =
            context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun setInteger(key: String, value: Int) = sharedPreferences.edit().putInt(key, value).commit()

    fun getInteger(key: String) = sharedPreferences.getInt(key, 0)

    fun setString(key: String, value: String) =
        sharedPreferences.edit().putString(key, value).commit()

    fun getString(key: String): String = sharedPreferences.getString(key, "")

    fun setLong(key: String, value: Long) = sharedPreferences.edit().putLong(key, value).commit()

    fun getLong(key: String): Long = sharedPreferences.getLong(key, 0L)
}