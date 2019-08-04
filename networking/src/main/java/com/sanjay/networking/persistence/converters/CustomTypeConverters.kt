package com.sanjay.networking.persistence.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sanjay.networking.response.model.ArticleBodyComponent
import com.sanjay.networking.response.model.ArticleObject
import com.sanjay.networking.response.model.ChannelPreview
import com.sanjay.networking.response.model.Image

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
    fun convertToDatabaseValue(entityProperty: ChannelPreview?): String? {
        return gson.toJson(entityProperty)
    }

    @TypeConverter
    fun convertToEntityProperty(databaseValue: String?): ChannelPreview? {
        return databaseValue?.let { gson.fromJson(it, ChannelPreview::class.java) }
                ?: ChannelPreview(-1, null, null)
    }

    @TypeConverter
    fun convertJsonToImageList(json: String): List<Image>? {
        val gson = Gson()
        val type = object : TypeToken<List<Image>>() {

        }.type
        return gson.fromJson<List<Image>>(json, type)
    }

    @TypeConverter
    fun convertStringToListArticleBodyComponent(json: String): List<ArticleBodyComponent>? {
        val gson = Gson()
        val type = object : TypeToken<List<ArticleBodyComponent>>() {

        }.type
        return gson.fromJson<List<ArticleBodyComponent>>(json, type)
    }

    @TypeConverter
    fun convertJsonToImage(json: String): Image? {
        val gson = Gson()
        val type = object : TypeToken<Image>() {}.type
        return gson.fromJson<Image>(json, type)
    }

    @TypeConverter
    fun convertJsonToArticleObject(json: String): ArticleObject? {
        val gson = Gson()
        val type = object : TypeToken<ArticleObject>() {}.type
        return gson.fromJson<ArticleObject>(json, type)
    }

    @TypeConverter
    fun convertImageListToJson(list: List<Image>?): String? {
        val gson = Gson()
        val type = object : TypeToken<List<Image>>() {

        }.type
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun convertListArticleBodyComponentToString(list: List<ArticleBodyComponent>?): String? {
        val gson = Gson()
        val type = object : TypeToken<List<ArticleBodyComponent>>() {

        }.type
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun convertStringToObject(image: Image?): String? {
        val gson = Gson()
        val type = object : TypeToken<Image>() {

        }.type
        return gson.toJson(image, type)
    }

    @TypeConverter
    fun convertStringToArticleObject(articleObject: ArticleObject?): String? {
        val gson = Gson()
        val type = object : TypeToken<ArticleObject>() {

        }.type
        return gson.toJson(articleObject, type)
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