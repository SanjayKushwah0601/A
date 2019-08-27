package com.freight.shipper.core.persistence.db.converters

import androidx.room.TypeConverter
import com.freight.shipper.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CustomTypeConverters {

    private val gson = Gson()

    @TypeConverter
    fun convertStringToList(databaseValue: String?): List<String>? {
        if (databaseValue == null) return listOf()
        return databaseValue.split(",")
    }

    @TypeConverter
    fun convertStringListToObject(entityProperty: List<String>?): String? {
        if (entityProperty == null) return ""
        if (entityProperty.isEmpty()) return ""
        val builder = StringBuilder()
        entityProperty.forEach { builder.append(it).append(",") }
        builder.deleteCharAt(builder.length - 1)
        return builder.toString()
    }

    @TypeConverter
    fun convertStringToLongList(databaseValue: String?): List<Long>? {
        if (databaseValue == null) return ArrayList()
        return databaseValue.split(",").mapNotNull { it.toLongOrNull() }
    }

    @TypeConverter
    fun convertLongListToString(entityProperty: List<Long>?): String? {
        if (entityProperty == null) return ""
        if (entityProperty.isEmpty()) return ""
        val builder = StringBuilder()
        entityProperty.forEach { builder.append(it.toString()).append(",") }
        builder.deleteCharAt(builder.length - 1)
        return builder.toString()
    }


    @TypeConverter
    fun convertJsonToVehicleTypeList(json: String): List<VehicleType>? {
        val type = object : TypeToken<List<VehicleType>>() {}.type
        return gson.fromJson<List<VehicleType>>(json, type)
    }

    @TypeConverter
    fun convertVehicleTypeListToJson(list: List<VehicleType>?): String? {
        val type = object : TypeToken<List<VehicleType>>() {}.type
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun convertJsonToCountryList(json: String): List<Country>? {
        val type = object : TypeToken<List<Country>>() {}.type
        return gson.fromJson<List<Country>>(json, type)
    }

    @TypeConverter
    fun convertCountryListToJson(list: List<Country>?): String? {
        val type = object : TypeToken<List<Country>>() {}.type
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun convertJsonToLoadCategoryList(json: String): List<LoadCategory>? {
        val type = object : TypeToken<List<LoadCategory>>() {}.type
        return gson.fromJson<List<LoadCategory>>(json, type)
    }

    @TypeConverter
    fun convertLoadCategoryListToJson(list: List<LoadCategory>?): String? {
        val type = object : TypeToken<List<LoadCategory>>() {}.type
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun convertJsonToLoadStatusList(json: String): List<LoadStatus>? {
        val type = object : TypeToken<List<LoadCategory>>() {}.type
        return gson.fromJson<List<LoadStatus>>(json, type)
    }

    @TypeConverter
    fun convertLoadStatusListToJson(list: List<LoadStatus>?): String? {
        val type = object : TypeToken<List<LoadStatus>>() {}.type
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun convertJsonToStateList(json: String): List<State>? {
        val type = object : TypeToken<List<LoadCategory>>() {}.type
        return gson.fromJson<List<State>>(json, type)
    }

    @TypeConverter
    fun convertStateListToJson(list: List<State>?): String? {
        val type = object : TypeToken<List<State>>() {}.type
        return gson.toJson(list, type)
    }
}