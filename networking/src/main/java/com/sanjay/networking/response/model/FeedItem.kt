package com.sanjay.networking.response.model

sealed class FeedItem {

    companion object {
        const val KEY_POST_TYPE = "postType"
        const val KEY_POST = "post"

        fun getObjectType(type: String): Class<*>? {
            return when (type) {
                "artist" -> Artist::class.java
                "gallery" -> Gallery::class.java
                "channel" -> Channel::class.java
                "article" -> Article::class.java
                else -> null
            }
        }
    }

    abstract var itemId: Long?
}

data class ArtistFeedItem(val artist: Artist) : FeedItem() {
    override var itemId: Long? = artist.id
}

data class GalleryFeedItem(val gallery: Gallery) : FeedItem() {
    override var itemId: Long? = gallery.id
}

data class ChannelFeedItem(val channel: Channel) : FeedItem() {
    override var itemId: Long? = channel.id
}

data class ArticleFeedItem(val article: Article) : FeedItem() {
    override var itemId: Long? = article.id
}

