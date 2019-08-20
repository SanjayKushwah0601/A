package com.sanjay.networking.client.server

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sanjay.networking.response.ApiResponse
import com.sanjay.networking.response.model.Category
import com.sanjay.networking.response.model.Login
import com.sanjay.networking.response.model.Token
import com.sanjay.networking.result.APIError
import com.sanjay.networking.result.APIErrorType
import com.sanjay.networking.result.APIResult
import com.sanjay.networking.service.AuthenticationService
import com.sanjay.networking.service.CategoryService
import com.sanjay.networking.service.UserService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import ru.gildor.coroutines.retrofit.Result
import ru.gildor.coroutines.retrofit.awaitResult
import timber.log.Timber
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.*


class MeuralAPI(val retrofit: Retrofit) : MeuralAPIContract() {

    // region - Service init
    private val authService = retrofit.create(AuthenticationService::class.java)
    private val categoryService = retrofit.create(CategoryService::class.java)
    private val userService = retrofit.create(UserService::class.java)
    // endregion

    private val decoder = Gson()

    // region - Auth
    override suspend fun login(email: String, password: String): APIResult<Login> {
//        return authService.authenticate(email, password).mappedApiResult()
        return authService.login(email, password).apiResult()
    }

    override suspend fun register(
        firstName: String, lastName: String, email: String, password: String,
        confirmPassword: String, countryCode: String, isReceiveCommunications: Boolean,
        isSecurityToken: Boolean
    ): APIResult<Token> {
        return userService.createUser(
            firstName, lastName, email, password, confirmPassword, countryCode,
            isReceiveCommunications, isSecurityToken
        ).apiResult()
    }
    // endregion

    // region - Category
    override suspend fun getCategory(id: Long): APIResult<Category> {
        return categoryService.getCategory(id).apiResult()
    }
    // endregion

    // region - Helpers
    private suspend fun Call<ApiResponse<Unit>>.emptyBodyResult(): APIResult<Unit> {
        return try {
            awaitResult()
            APIResult.Success(Unit)
        } catch (error: Throwable) {
            if (error is NullPointerException) {
                APIResult.Success(Unit)
            } else {
                APIResult.Failure(errorFromAnyException(error))
            }
        }
    }

    private suspend inline fun Call<ApiResponse<Unit>>.executeEmptyResponseCallAsync(): APIResult<Unit> {
        Timber.v("Executing empty response call async")
        var error: APIResult<Unit>? = null
        val result: Response<ApiResponse<Unit>>? = GlobalScope.async {
            try {
                this@executeEmptyResponseCallAsync.execute()
            } catch (exception: Throwable) {
                when (exception) {
                    is HttpException ->
                        error = APIResult.Failure(errorFromHttpException(exception))

                    else -> {
                        error = APIResult.Failure(errorFromAnyException(exception))
                    }
                }
                null
            }
        }.await()

        return if (result?.isSuccessful == true) {
            APIResult.Success(Unit)
        } else {
            APIResult.Failure(APIError.default)
        }
    }

    private suspend fun <T : Any> Call<T>.mappedApiResult(): APIResult<T> {
        val result = awaitResult()
        return when (result) {
            is Result.Ok -> APIResult.Success(ApiResponse(result.value, null, null, null))
            is Result.Error -> APIResult.Failure(errorFromHttpException(result.exception))
            is Result.Exception -> APIResult.Failure(errorFromAnyException(result.exception))
        }
    }

    // region - Response Parsing
    private suspend fun <T : Any> Call<ApiResponse<T>>.apiResult(): APIResult<T> {
        val result = awaitResult()
        return when (result) {
            is Result.Ok -> APIResult.Success(result.value)
            is Result.Error -> APIResult.Failure(errorFromHttpException(result.exception))
            is Result.Exception -> APIResult.Failure(errorFromAnyException(result.exception))
        }
    }
    // endregion

    // region - Error handling
    private fun errorFromHttpException(exception: HttpException): APIError {
        val errorBody = exception.response().errorBody()?.string()
        val code = exception.code()
        val message = exception.message()

        return if (errorBody != null) {
            if (JSONTokener(errorBody).nextValue() !is JSONObject)
                return APIError(mapOf(), APIErrorType.General, code, exception, message)

            val errors: HashMap<String, Any?> = decoder.fromJson(errorBody, errorMapType)
            val stringError: HashMap<String, String> = HashMap(errors.mapValues {
                it.value?.toString() ?: ""
            })
            val type = errorTypeFrom(stringError)
            val returnedMessage = messageFrom(stringError) ?: message
            val errorResult = APIError(stringError, type, code, exception, returnedMessage)
            errorResult
        } else {
            APIError(mapOf(), APIErrorType.General, code, exception, message)
        }
    }

    private fun errorFromAnyException(exception: Throwable): APIError {
        val (typeString, reason) = when (exception) {
            is SocketTimeoutException,
            is SocketException,
            is UnknownHostException -> Pair("Network", "Network error")
            else -> Pair("NetworkCall", "An error occurred ${exception.message}")
        }
        return APIError(hashMapOf(typeString to reason), APIErrorType.Network(exception), -1, exception, reason)
    }

    private fun errorTypeFrom(errors: HashMap<String, String>): APIErrorType {
        /*
         * In APIClient there were Exception handling for subscription
         * for make same handling, return  APIErrorType.Subscription.
        */
        if (errors.contains("subscription")) {
            return APIErrorType.Subscription
        } else if (errors.contains("purchase")) {
            return APIErrorType.Purchase
        } else if (errors.contains("firstName") ||
            errors.contains("lastName") ||
            errors.contains("email") ||
            errors.contains("password1") ||
            errors.contains("password2") ||
            errors.contains("country")
        ) {
            return APIErrorType.SignUpError
        }
        return APIErrorType.General
    }

    private fun messageFrom(errors: HashMap<String, String>): String? {
        return errors.values.first()
    }

    private val errorMapType = object : TypeToken<HashMap<String, Any>>() {}.type
    // endregion
}

