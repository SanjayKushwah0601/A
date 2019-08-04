package com.sanjay.networking.response.model

import com.google.gson.annotations.SerializedName

/**
 * JSON representation of a User response
 */

class User(@SerializedName("id") var userId: Long?,
           @SerializedName("username") var username: String?,
           @SerializedName("email") var email: String?,
           @SerializedName("favoriteItems") var favoriteItems: MutableSet<Long>,
           @SerializedName("favoriteArticles") var favoriteArticles: MutableSet<Long>,
           @SerializedName("favoriteArtists") var favoriteArtists: MutableSet<Long>,
           @SerializedName("favoritePartners") var favoriteChannels: MutableSet<Long>,
           @SerializedName("favoriteCategories") var favoriteCategories: MutableSet<Long>,
           @SerializedName("favoriteGalleries") var favoritePlaylists: MutableSet<Long>,
           @SerializedName("subscriptionStatus") var subscriptionStatus: String?,
           @SerializedName("subscriptionIsActive") var subscriptionIsActive: Boolean?,
           @SerializedName("subscriptionInterval") var subscriptionInterval: String?,
           @SerializedName("purchasedItems") var purchasedItems: MutableSet<Long>,
           @SerializedName("purchasedGalleries") var purchasedGalleries: MutableSet<Long>,
           @SerializedName("paymentBrand") var paymentBrand: String?,
           @SerializedName("paymentLast4") var paymentLast4Digits: String?) {

    val isPaymentMethodAvailable: Boolean
        get() = !paymentLast4Digits.isNullOrEmpty()

}
