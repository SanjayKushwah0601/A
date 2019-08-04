package com.freight.shipper.core.persistence.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson

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
}