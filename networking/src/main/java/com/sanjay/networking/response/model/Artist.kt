package com.sanjay.networking.response.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.sanjay.networking.persistence.converters.CustomTypeConverters
import kotlinx.android.parcel.Parcelize


/**
 * JSON representation of an Artist response
 */
@Entity(tableName = "artist")
@TypeConverters(CustomTypeConverters::class)
@Parcelize
data class Artist(
    @PrimaryKey
    @SerializedName("id")
    val id: Long,

    @SerializedName("avatar")
    val avatarImageUrl: String?,

    @SerializedName("avatar2x")
    val avatarImageUrlLarge: String?,

    @SerializedName("birth")
    val birth: String?,

    @SerializedName("death")
    val death: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("firstName")
    val firstName: String?,

    @SerializedName("gender")
    val gender: String?,

    @SerializedName("hero")
    val heroImageUrl: String?,

    @SerializedName("hero2x")
    val heroImageUrlLarge: String?,

    @SerializedName("itemCount")
    val itemCount: Int?,

    @SerializedName("categoryIds")
    val categoryIds: List<Long>?,

    @SerializedName("categoryNames")
    val categoryNames: List<String>?,

    @SerializedName("fullName")
    val fullName: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("nationality")
    val nationality: String?,

    @SerializedName("slug")
    val slug: String?,

    @SerializedName("lastName")
    val lastName: String?,

    @SerializedName("featuredAt")
    val featuredAt: String?,

    @SerializedName("favoriteCount")
    override val favoriteCount: Long?
) : Parcelable, Favoritable {

    override fun getItemId(): Long {
        return id
    }

    override fun getModelType(): String {
        return "artist"
    }

    override fun getUserFacingPluralName(): String {
        return "artists"
    }

    override fun isFavorite(user: User?): Boolean {
        val userFavorites = user?.favoriteArtists

        return if (userFavorites?.isNotEmpty() == true) {
            userFavorites.contains(this.id)
        } else {
            false
        }
    }

    override fun updateUserObjectFromFavorite(user: User, isFavorite: Boolean) {
        if (isFavorite) {
            user.favoriteArtists.add(id)
        } else {
            user.favoriteArtists.remove(id)
        }
    }
}