package com.sanjay.networking.response.model

class Header(val name: String, var isClickable: Boolean = false, var hasOverflow: Boolean = false, var type: Class<*>) : Favoritable {
    override fun isFavorite(user: User?): Boolean {
        return false
    }

    override fun getModelType(): String {
        return "Header"
    }

    override fun getUserFacingPluralName(): String {
        return ""
    }

    override fun getItemId(): Long {
        return -1
    }

    override val favoriteCount: Long?
        get() = null

    override fun updateUserObjectFromFavorite(user: User, isFavorite: Boolean) {
    }
}