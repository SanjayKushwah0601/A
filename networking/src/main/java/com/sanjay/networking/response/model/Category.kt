package com.sanjay.networking.response.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.sanjay.networking.persistence.converters.CustomTypeConverters
import kotlinx.android.parcel.Parcelize

/**
 * JSON representation of a category response
 */

@Entity(tableName = "category")
@TypeConverters(CustomTypeConverters::class)
@Parcelize
data class Category(
        @PrimaryKey
        @SerializedName("id")
        var id: Long,

        @SerializedName("name")
        var name: String?,

        @SerializedName("description")
        var description: String?,

        @SerializedName("avatar")
        var avatarImage: Image?,

        @SerializedName("hero")
        var heroImage: Image?,

        @SerializedName("favoriteCount")
        override val favoriteCount: Long?
) : Parcelable, Favoritable {

    override fun isFavorite(user: User?): Boolean {
        val userFavorites = user?.favoriteCategories

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
        return "category"
    }

    override fun getUserFacingPluralName(): String {
        return "categories"
    }

    override fun updateUserObjectFromFavorite(user: User, isFavorite: Boolean) {
        if (isFavorite) {
            user.favoriteCategories.add(id)
        } else {
            user.favoriteCategories.remove(id)
        }
    }
}