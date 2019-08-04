package com.sanjay.networking.response.model

import com.sanjay.networking.R
import com.sanjay.networking.util.StringUtil.getString
import com.sanjay.networking.client.server.MeuralAPIContract
import com.sanjay.networking.persistence.LoginManager
import com.sanjay.networking.result.APIError
import com.sanjay.networking.result.APIErrorType
import com.sanjay.networking.result.APIResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

interface Favoritable {
    fun isFavorite(user: User?): Boolean
    fun getModelType(): String
    fun getUserFacingPluralName(): String
    fun getItemId(): Long
    val favoriteCount: Long?

    suspend fun setFavorite(favorite: Boolean,
                            loginManager: LoginManager,
                            meuralAPI: MeuralAPIContract,
                            success: (Boolean) -> Unit = {},
                            failure: (APIError) -> Unit = {}) {
        var user = loginManager.loggedInUser
        val id = getItemId()
        if (user == null) {
            GlobalScope.launch(Dispatchers.Main) {
                failure(APIError(hashMapOf(), APIErrorType.General, -1, null,
                        getString(R.string.favorite_error_login).format(getModelType())))
            }
            return
        }

        val result = meuralAPI.favorite(getModelType(), id, favorite).withFailure(failure)

        if (result is APIResult.Success) {
            updateUserObjectFromFavorite(user, result.response.data)
            success(result.response.data)
        }

        loginManager.loggedInUser = user
    }

    fun updateUserObjectFromFavorite(user: User, isFavorite: Boolean)
}