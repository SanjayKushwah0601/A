package com.sanjay.networking.persistence

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sanjay.networking.response.model.Device
import com.sanjay.networking.response.model.Language
import com.sanjay.networking.response.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginManager(context: Context) {
    companion object {
        private const val SHARED_PREFS_NAME = "SHARED_PREFS"
        private const val SHARED_PREFS_KEY_TOKEN = "token"
        private const val SHARED_PREFS_KEY_USER = "v0/user"
        private const val SHARED_PREFS_KEY_LAST_ACTIVE_CANVAS = "last_active_canavas"
        private const val SHARED_PREFS_KEY_USER_ID = "user_id"
        private const val SHARED_PREFS_KEY_CANVASES = "canvases"
        private const val SHARED_PREFS_KEY_CANVAS_LANGUAGE = "language"
    }

    private val sharedPreferences: SharedPreferences

    val allCanvases: List<Device>
        get() {
            return AvailableCanvasesLiveData.value.orEmpty()
        }

    val hasCanvases: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

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

    var lastActiveCanvas: Long
        get() = sharedPreferences.getLong(SHARED_PREFS_KEY_LAST_ACTIVE_CANVAS, -1)
        @SuppressLint("ApplySharedPref") //Last Active Canvas needs to be saved immediately in some situations
        set(canvasId) {
            sharedPreferences.edit().putLong(SHARED_PREFS_KEY_LAST_ACTIVE_CANVAS, canvasId).commit()
        }

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
        GlobalScope.launch {
            val persisted = fetchPersistedCanvases()
            withContext(Dispatchers.Main) {
                AvailableCanvasesLiveData.value = persisted
                hasCanvases.value = persisted.isNotEmpty()
            }
        }

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

    fun setAvailableCanvases(canvases: List<Device>) {
        AvailableCanvasesLiveData.postValue(canvases)
        persistAvailableCanvases(canvases)
        hasCanvases.postValue(canvases.isNotEmpty())
    }

    private fun persistAvailableCanvases(canvases: List<Device>) {
        val serializedCanvases = gson.toJson(canvases)

        sharedPreferences.edit()
                .putString(SHARED_PREFS_KEY_CANVASES, serializedCanvases)
                .apply()
    }

    private fun fetchPersistedCanvases(): List<Device> {
        val serializedCanvases = sharedPreferences.getString(SHARED_PREFS_KEY_CANVASES, null)
                ?: return emptyList()

        return gson.fromJson<List<Device>>(serializedCanvases)
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
        AvailableCanvasesLiveData.postValue(emptyList())

        token = null
        loggedInUser = null

        sharedPreferences.edit()
                .clear()
                .apply()
    }

    fun setCanvasLanguage(languages: List<Language>) {
        val serializedLanguages = gson.toJson(languages)
        sharedPreferences.edit()
                .putString(SHARED_PREFS_KEY_CANVAS_LANGUAGE, serializedLanguages)
                .apply()
    }

    fun fetchCanvasLanguages(): List<Language> {
        val serializedLanguages = sharedPreferences.getString(SHARED_PREFS_KEY_CANVAS_LANGUAGE, null)
                ?: return emptyList()

        return gson.fromJson(serializedLanguages)
    }
}

inline fun <reified T> Gson.fromJson(json: String): T = this.fromJson<T>(json, object : TypeToken<T>() {}.type)

private object AvailableCanvasesLiveData : MutableLiveData<List<Device>>()
