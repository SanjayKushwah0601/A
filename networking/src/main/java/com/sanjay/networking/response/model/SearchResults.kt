package com.sanjay.networking.response.model

import com.google.gson.annotations.SerializedName

class SearchResults(@SerializedName("items")
                    val items: List<Image>,

                    @SerializedName("galleries")
                    val galleries: List<Gallery>,

                    @SerializedName("channels")
                    val channels: List<Channel>,

                    @SerializedName("artists")
                    val artists: List<Artist>,

                    @SerializedName("articles")
                    val articles: List<Article>,

                    @SerializedName("categories")
                    val categories: List<Category>
) {
    fun anyResultsReturned(): Boolean {
        return items.isNotEmpty() ||
                galleries.isNotEmpty() ||
                artists.isNotEmpty() ||
                articles.isNotEmpty() ||
                categories.isNotEmpty() ||
                channels.isNotEmpty()
    }
}