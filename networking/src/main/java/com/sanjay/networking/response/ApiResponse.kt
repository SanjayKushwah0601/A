package com.sanjay.networking.response

import com.google.gson.annotations.SerializedName

sealed class MeuralResponse<T> {
    abstract fun isSuccessful(): Boolean
}

/**
 * Generic response for the Meural API. Used in most API calls.
 *
 * The "data" field will contain the actual response data
 *
 * The "Message" field will contain a useful message for the user, if applicable
 *
 * The "isPaginated" field tells us whether this call is paginated
 *
 * The "isLast" field tells us whether this is the last page of a paginated call
 *
 * The "error" field should only exist for a failed call, and contains an error string
 * Additional information will be contained in a more specific field, e.g., "username" or "password"
 * which will denote the specific problem
 */

open class ApiResponse<T>(
        @SerializedName("data")
        var data: T,

        @SerializedName("isPaginated")
        private var isPaginated: Boolean?,

        @SerializedName("isLast")
        private var isLast: Boolean?,

        @SerializedName("count")
        var itemCount: Int? = 0
) : MeuralResponse<T>() {
    override fun isSuccessful(): Boolean {
        return data != null
    }

    fun hasNextPage(): Boolean {
        return if (isPaginated == null || isPaginated == false) {
            false
        } else {
            isLast == false
        }
    }
}

sealed class ErrorResponse<T> : MeuralResponse<T>() {
    override fun isSuccessful(): Boolean {
        return false
    }

    abstract fun getException(): Exception
}

/**
 * Error Response for when an error message from the API is expected
 */
class ApiErrorResponse<T>(
        @SerializedName("errors")
        val errors: String? = null,

        @SerializedName("device")
        val deviceErrors: String? = null,

        @SerializedName("error")
        val error: String? = null,

        @SerializedName("message")
        val message: String? = null,

        @SerializedName("detail")
        var errorDetail: String? = null,

        @SerializedName("username")
        var usernameErrors: String? = null,

        @SerializedName("email")
        var emailErrors: String? = null,

        @SerializedName("password1")
        var passwordErrors: String? = null,

        @SerializedName("item")
        var itemErrors: String? = null,

        @SerializedName("subscription")
        var subscriptionErrors: String? = null
) : ErrorResponse<T>() {
    fun getFirstNonNullErrorMessage(): String? {
        return deviceErrors ?: error ?: errors ?: errorDetail ?: usernameErrors
        ?: emailErrors ?: passwordErrors ?: itemErrors ?: subscriptionErrors
    }

    override fun getException(): Exception {
        return MeuralApiException(getFirstNonNullErrorMessage())
    }
}

/**
 * Error response for cases where the issue was related to networking (e.g. couldn't reach the server)
 * The cause can be used to figure out the specific problem if you want, but generally that's not
 * as important as knowing in general that there was a network problem
 */
class NetworkProblemResponse<T>(val cause: Throwable) : ErrorResponse<T>() {
    override fun getException(): Exception {
        return cause as? Exception ?: NetworkProblemException()
    }
}

/**
 * Error response for cases where an exception was encountered. This will generally be due to
 * something other than a Network problem.
 */
class ExceptionResponse<T>(val cause: Throwable) : ErrorResponse<T>() {
    override fun getException(): Exception {
        return cause as? Exception ?: CallFailedException()
    }
}

/**
 * Error response for cases where the call was never made (e.g., due to a required field being null)
 */
class NetworkProblemException : Exception()

class CallNotImplementedException : Exception()
class CallFailedException : Exception()
class MeuralApiException(override val message: String?) : Exception(message)