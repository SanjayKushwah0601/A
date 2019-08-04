package com.sanjay.networking.response.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.sanjay.networking.persistence.converters.CustomTypeConverters
import kotlinx.android.parcel.Parcelize

/**
 * JSON representation of a Channel response
 */
@Entity(tableName = "channel")
@TypeConverters(CustomTypeConverters::class)
@Parcelize
data class Channel(
        @PrimaryKey
        @SerializedName("id")
        val id: Long,

        @SerializedName("order")
        val order: Long?,

        @SerializedName("name")
        val name: String?,

        @SerializedName("description")
        val description: String?,

        @SerializedName("logo")
        val logoImageUrl: String?,

        @SerializedName("avatar")
        val avatar: Image?,

        @SerializedName("hero")
        val hero: Image?,

        @SerializedName("itemCount")
        val itemCount: Int?,

        @SerializedName("favoriteCount")
        override val favoriteCount: Long?,

        @SerializedName("featuredAt")
        val featuredAt: String?
) : Favoritable, Parcelable {

    override fun isFavorite(user: User?): Boolean {
        val userFavorites = user?.favoriteChannels

        return if (userFavorites?.isNotEmpty() == true) {
            userFavorites.contains(this.id)
        } else {
            false
        }
    }

    override fun getItemId(): Long {
        return id
    }

    override fun getModelType(): String {
        return "channel"
    }

    override fun getUserFacingPluralName(): String {
        return "channels"
    }

    override fun updateUserObjectFromFavorite(user: User, isFavorite: Boolean) {
        if (isFavorite) {
            user.favoriteChannels.add(id)
        } else {
            user.favoriteChannels.remove(id)
        }
    }
}