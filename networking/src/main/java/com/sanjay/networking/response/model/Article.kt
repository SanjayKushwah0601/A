package com.sanjay.networking.response.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.sanjay.networking.persistence.converters.CustomTypeConverters
import com.sanjay.networking.persistence.fromJson
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "article")
@TypeConverters(CustomTypeConverters::class)
@Parcelize
data class Article(
        @PrimaryKey
        @SerializedName("id") val id: Long,
        @SerializedName("body") val body: List<ArticleBodyComponent>,
        @SerializedName("hero") val hero: Image?,
        @SerializedName("name") val name: String?,
        @SerializedName("subheader") val subheader: String?,
        @SerializedName("featuredAt") val featuredAt: String?,
        @SerializedName("type") val type: String,
        @SerializedName("object") val articleObject: ArticleObject,
        @SerializedName("favoriteCount") override val favoriteCount: Long?,
        @SerializedName("seriesName") val seriesName: String?
) : Favoritable, Parcelable {


    private companion object : Parceler<Article> {
        val gson = Gson()
        override fun Article.write(parcel: Parcel, flags: Int) {
            val jsonString = gson.toJson(this)
            parcel.writeString(jsonString)
        }

        override fun create(parcel: Parcel): Article {
            val jsonString = parcel.readString()
            return gson.fromJson(jsonString)
        }
    }

    override fun isFavorite(user: User?): Boolean {
        val userFavorites = user?.favoriteArticles

        return if (userFavorites?.isNotEmpty() == true) {
            userFavorites.contains(this.id)
        } else {
            false
        }
    }

    override fun getModelType(): String = "article"
    override fun getItemId(): Long = id
    override fun getUserFacingPluralName(): String = "articles"
    override fun updateUserObjectFromFavorite(user: User, isFavorite: Boolean) {
        if (isFavorite) {
            user.favoriteArticles.add(id)
        } else {
            user.favoriteArticles.remove(id)
        }
    }
}

class ArticleBodyComponent(
        @SerializedName("type") val type: String,
        @SerializedName("content") val content: BodyComponentContent
) {

    companion object {
        const val PARAGRAPH = "paragraph"
        const val EXTERNAL_IMAGE = "image"
        const val MEURAL_ITEM = "item"
        const val AUDIO = "audio"
        const val VIDEO = "video"
    }
}

class BodyComponentContent(
        @SerializedName("text") val text: String?,
        @SerializedName("caption") val caption: String?,
        @SerializedName("url") val url: String?,
        @SerializedName("name") val name: String?,
        @SerializedName("item") val item: Image?,
        @SerializedName("duration") val duration: String?
)

class ArticleObject : HashMap<String, Any>() {
    inline fun <reified T> converted(): T? {
        val gson = Gson()
        val encoded = gson.toJson(this)
        return try {
            gson.fromJson(encoded, T::class.java)
        } catch (e: Exception) {
            null
        }
    }

    val gallery: Gallery?
        get() = converted()
}

fun Article.previewString(): String? {
    val firstParagraph = body.firstOrNull { it.type == ArticleBodyComponent.PARAGRAPH }
    return firstParagraph?.content?.text
}