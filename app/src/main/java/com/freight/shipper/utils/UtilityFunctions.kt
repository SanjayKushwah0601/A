package com.freight.shipper.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


/**
 * @CreatedBy Sanjay Kushwah on 9/12/2019.
 * sanjaykushwah0601@gmail.com
 */

// Serialize a single object.
fun <T> serializeToJson(myClass: T): String {
    return Gson().toJson(myClass)
}

// Deserialize to single object.
inline fun <reified T> deserializeFromJson(jsonString: String): T? {
    val gson = Gson()
    return gson.fromJson<T>(jsonString)
}

inline fun <reified T> Gson.fromJson(json: String): T =
    this.fromJson<T>(json, object : TypeToken<T>() {}.type)
