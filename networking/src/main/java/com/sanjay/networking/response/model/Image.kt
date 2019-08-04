package com.sanjay.networking.response.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.sanjay.networking.enums.Orientation
import com.sanjay.networking.persistence.converters.ImageTypeConverters
import com.sanjay.networking.response.model.price.Priceable
import kotlinx.android.parcel.Parcelize

/**
 * JSON representation of an Image (a.k.a., "item") response
 */
@Entity(tableName = "image")
@Parcelize
@TypeConverters(ImageTypeConverters::class)
data class Image(
        @PrimaryKey
        @SerializedName("id") override var id: Long = -1,
        @SerializedName("artistId") val artistId: Long?,
        @SerializedName("artistName") val artistName: String?,
        @SerializedName("author") val author: String?,

        @SerializedName("categoryIds") val categoryIds: List<Long>?,
        @SerializedName("categoryNames") val categoryNames: List<String>?,

        @SerializedName("copyright") val copyright: String?,
        @SerializedName("description") val description: String?,
        @SerializedName("dimensions") val dimensions: String?,
        @SerializedName("hero") val heroImageUrl: String?,
        @SerializedName("hero2x") val heroImageUrlLarge: String?,
        @SerializedName("icon") val iconImageUrl: String?,
        @SerializedName("icon2x") val iconImageUrlLarge: String?,
        @SerializedName("originalImage") val originalImageUrl: String?,
        @SerializedName("isDetailed") val isDetailed: Boolean?,
        @SerializedName("isSampler") override val isSampler: Boolean = false,
        @SerializedName("isPublic") override val isPublic: Boolean = false,
        @SerializedName("location") val location: String?,
        @SerializedName("medium") val medium: String?,
        @SerializedName("name") val name: String?,
        @SerializedName("croppedHeight") val croppedHeight: Int?,
        @SerializedName("croppedWidth") val croppedWidth: Int?,
        @SerializedName("croppedX1") val croppedX1: Int?,
        @SerializedName("croppedY1") val croppedY1: Int?,
        @SerializedName("croppedX2") val croppedX2: Int?,
        @SerializedName("croppedY2") val croppedY2: Int?,
        @SerializedName("originalHeight") val originalHeight: Int?,
        @SerializedName("originalWidth") val originalWidth: Int?,
        @SerializedName("slug") val slug: String?,
        @SerializedName("image") val thumbImageUrl: String?,
        @SerializedName("userId") val userId: Long?,
        @SerializedName("year") val year: String?,
        @SerializedName("favoriteCount") override val favoriteCount: Long?,
        @SerializedName("isPremium") override val isPremium: Boolean = false,
        @SerializedName("subscriberPrice") override val subscriberPrice: Long = 0,
        @SerializedName("nonsubscriberPrice") override val nonsubscriberPrice: Long = 0)
    : Parcelable, Favoritable, Priceable {

    fun getAuthorYear(): String {
        return if (author == null && year == null) {
            ""
        } else if (author == null) {
            year ?: ""
        } else if (year == null) {
            author
        } else {
            "$author, $year"
        }
    }

    fun getOrientation(): Orientation {
        val width: Int
        val height: Int

        if (croppedHeight != null &&
                croppedWidth != null &&
                croppedHeight != 0 &&
                croppedWidth != 0) {
            width = croppedWidth
            height = croppedHeight
        } else if (originalHeight != null &&
                originalWidth != null &&
                originalHeight != 0 &&
                originalWidth != 0) {
            width = originalWidth
            height = originalHeight
        } else {
            return Orientation.UNKNOWN
        }

        return Orientation.fromHeightAndWidth(height, width)
    }

    override fun getItemId(): Long {
        return id
    }

    override fun getModelType(): String {
        return "item"
    }

    override fun isFavorite(user: User?): Boolean {
        val userFavorites = user?.favoriteItems

        return if (userFavorites?.isNotEmpty() == true) {
            userFavorites.contains(this.id)
        } else {
            false
        }
    }

    override fun getUserFacingPluralName(): String {
        return "works"
    }

    override fun updateUserObjectFromFavorite(user: User, isFavorite: Boolean) {
        if (isFavorite) {
            user.favoriteItems.add(id)
        } else {
            user.favoriteItems.remove(id)
        }
    }

    // region - Priceable interface
    override fun isOwned(user: User?): Boolean {
        return user?.purchasedItems?.contains(id) == true
    }

    override fun getContentType(): String {
        return "items"
    }

    override fun updatePurchasedArtwork(user: User) {
        user.purchasedItems.add(id)
    }
    // endregion
}