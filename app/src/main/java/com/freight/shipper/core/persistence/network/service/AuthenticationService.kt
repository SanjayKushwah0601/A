package com.freight.shipper.core.persistence.network.service

import com.freight.shipper.core.persistence.network.response.ApiResponse
import com.freight.shipper.model.Login
import com.freight.shipper.model.Token
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Contract for Authentication-related REST API calls
 *
 * A Retrofit object will create the actual implementation of this Service
 */

interface AuthenticationService {
    @FormUrlEncoded
    @POST("/v0/authenticate")
    fun authenticate(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<Token>

    @FormUrlEncoded
    @POST("")
    fun login(
        @Field("username") userName: String,
        @Field("password") password: String,
        @Field("type") type: String = "shipper",
        @Field("param") param: String = "login"
    ): Call<ApiResponse<Login>>
}