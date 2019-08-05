package com.freight.shipper.core.persistence.preference

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.freight.shipper.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LoginManager(context: Context) {
    companion object {
        private const val SHARED_PREFS_NAME = "SHARED_PREFS"
        private const val SHARED_PREFS_KEY_TOKEN = "token"
        private const val SHARED_PREFS_KEY_USER = "v0/user"
        private const val SHARED_PREFS_KEY_USER_ID = "user_id"
    }

    private val sharedPreferences: SharedPreferences


    var loggedInUser: User?
        get() {
            return if (user == null) {
                fetchPersistedUser()
            } else {
                user
            }
        }
        set(user) {
            this.user = user
            persistLoggedInUser(user)
        }

    private var user: User? = null

    private var token: String? = null


    var userId: Long?
        get() = sharedPreferences.getLong(SHARED_PREFS_KEY_USER_ID, -1)
        @SuppressLint("ApplySharedPref") //Last Active Canvas needs to be saved immediately in some situations
        set(userId) {
            if (userId != null) {
                sharedPreferences.edit().putLong(SHARED_PREFS_KEY_USER_ID, userId).commit()
            } else {
                sharedPreferences.edit().remove(SHARED_PREFS_KEY_USER_ID).commit()
            }
        }

    val gson = Gson()

    init {
        this.sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    }

    private fun persistLoggedInUser(user: User?) {
        val id = user?.userId

        if (id != null) {
            userId = id
        }

        val serializedUser = gson.toJson(user)

        sharedPreferences.edit()
            .putString(SHARED_PREFS_KEY_USER, serializedUser)
            .apply()
    }

    fun fetchPersistedUser(): User? {
        val serializedUser = sharedPreferences.getString(SHARED_PREFS_KEY_USER, null)
            ?: return null

        return gson.fromJson<User>(serializedUser)
    }


    fun getToken(): String? {
        if (token == null) {
            token = fetchPersistedToken()
        }

        return token
    }

    fun setToken(token: String) {
        this.token = token

        persistToken(token)
    }

    private fun persistToken(token: String) {
        sharedPreferences.edit()
            .putString(SHARED_PREFS_KEY_TOKEN, token)
            .apply()
    }

    private fun fetchPersistedToken(): String? {
        return sharedPreferences.getString(SHARED_PREFS_KEY_TOKEN, null)
    }

    fun clearDataForLogout() {
        token = null
        loggedInUser = null

        sharedPreferences.edit()
            .clear()
            .apply()
    }

}

inline fun <reified T> Gson.fromJson(json: String): T = this.fromJson<T>(json, object : TypeToken<T>() {}.type)

