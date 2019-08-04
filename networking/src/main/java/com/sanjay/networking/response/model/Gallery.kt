package com.sanjay.networking.response.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.sanjay.networking.enums.Orientation
import com.sanjay.networking.persistence.converters.CustomTypeConverters
import com.sanjay.networking.response.model.price.Priceable
import kotlinx.android.parcel.Parcelize

/**
 * JSON representation of a Gallery response
 */
@Entity(tableName = "gallery")
@Parcelize
@TypeConverters(CustomTypeConverters::class)
data class Gallery(
        @PrimaryKey
        @SerializedName("id") override var id: Long = -1,
        @SerializedName("cover") var coverImageUrl: String?,
        @SerializedName("cover2x") var coverImageUrlLarge: String?,
        @SerializedName("coverId") var coverId: Long?,
        @SerializedName("username") val username: String?,
        @SerializedName("userId") val userId: Long?,
        @SerializedName("description") var description: String?,
        @SerializedName("hero") val heroImageUrl: String?,
        @SerializedName("hero2x") val heroImageUrlLarge: String?,
        @SerializedName("icon") val iconImageUrl: String?,
        @SerializedName("icon2x") val iconImageUrlLarge: String?,
        @SerializedName("isPurchased") val isPurchased: Boolean?,
        @SerializedName("isPublic") override val isPublic: Boolean = false,
        @SerializedName("featuredAt") val featuredAt: String?,
        @SerializedName("isSampler") override val isSampler: Boolean = false,

        @SerializedName("itemIds") val itemIds: MutableList<Long> = mutableListOf(),

        @SerializedName("categoryIds") val categoryIds: List<Long>?,

        @SerializedName("categoryNames") val categoryNames: List<String>?,

        @SerializedName("name") var name: String?,
        @SerializedName("orientation") val orientation: String?,
        @SerializedName("shareable") val shareable: Boolean?,
        @SerializedName("slug") val slug: String?,
        @SerializedName("subject") val subject: String?,
        @SerializedName("type") val type: String?,
        @SerializedName("favoriteCount") override val favoriteCount: Long?,
        @SerializedName("previewItems") val previewItems: List<Image> = listOf(),

        @SerializedName("channel") var channel: ChannelPreview?,
        @SerializedName("isPremium") override val isPremium: Boolean = false,
        @SerializedName("subscriberPrice") override val subscriberPrice: Long = 0,
        @SerializedName("nonsubscriberPrice") override val nonsubscriberPrice: Long = 0)
    : Parcelable, Favoritable, Priceable {

    companion object {
        const val ALL_WORKS_GALLERY_ID = 5L
    }

    constructor() : this(
            -1,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            false,
            null,
            false,
            mutableListOf(),
            listOf(),
            listOf(),
            null,
            null,
            null,
            null,
            null,
            null,
            0,
            listOf(),
            null,
            false,
            0,
            0
    )

    val orientationType: Orientation
        get() {
            return Orientation.fromString(orientation)
        }

    override fun getItemId(): Long {
        return id
    }

    override fun getModelType(): String {
        return "gallery"
    }

    override fun isFavorite(user: User?): Boolean {
        val userFavorites = user?.favoritePlaylists

        return if (userFavorites?.isNotEmpty() == true) {
            userFavorites.contains(this.id)
        } else {
            false
        }
    }

    override fun getUserFacingPluralName(): String {
        return "playlists"
    }

    override fun updateUserObjectFromFavorite(user: User, isFavorite: Boolean) {
        if (isFavorite) {
            user.favoritePlaylists.add(id)
        } else {
            user.favoritePlaylists.remove(id)
        }
    }

    // region - Priceable interface
    override fun isOwned(user: User?): Boolean {
        return user?.purchasedGalleries?.contains(id) == true
    }

    override fun getContentType(): String {
        return "galleries"
    }

    override fun updatePurchasedArtwork(user: User) {
        user.purchasedGalleries.add(id)
        user.purchasedItems.addAll(itemIds)
    }
    // endregion
}

@Parcelize
data class ChannelPreview(@SerializedName("id") val id: Long,
                          @SerializedName("name") val name: String?,
                          @SerializedName("logo") val logo: String?) : Parcelable
