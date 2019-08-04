package com.sanjay.networking.client

import com.google.gson.*
import com.sanjay.networking.response.model.*
import java.lang.reflect.Type

class FeedItemDeserializer(val gson: Gson) : JsonDeserializer<FeedItem> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): FeedItem? {
        val root = json as JsonObject
        val postType = json.get(FeedItem.KEY_POST_TYPE).asString
        val postJsonObject = root.get(FeedItem.KEY_POST).asJsonObject

        val postClass = FeedItem.getObjectType(postType) ?: return null

        val postObject = try {
            gson.fromJson(postJsonObject, postClass)
        } catch (e: Exception) {
            return null
        }

        return when (postObject) {
            is Artist -> ArtistFeedItem(postObject)
            is Gallery -> GalleryFeedItem(postObject)
            is Channel -> ChannelFeedItem(postObject)
            is Article -> ArticleFeedItem(postObject)
            else -> null
        }
    }
}