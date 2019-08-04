package com.sanjay.networking.response.model.price

import com.sanjay.networking.response.model.Favoritable
import com.sanjay.networking.response.model.User

/**
 * @author GWL
 */

interface Priceable : Favoritable {
    val id: Long
    val isSampler: Boolean
    val isPremium: Boolean
    val isPublic: Boolean
    val subscriberPrice: Long
    val nonsubscriberPrice: Long
    fun isOwned(user: User?): Boolean
    fun getContentType(): String
    fun updatePurchasedArtwork(user: User)
}