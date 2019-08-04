package com.sanjay.networking.service

import com.sanjay.networking.response.model.Token
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
    fun authenticate(@Field("username") username: String,
                     @Field("password") password: String)
            : Call<Token>
}