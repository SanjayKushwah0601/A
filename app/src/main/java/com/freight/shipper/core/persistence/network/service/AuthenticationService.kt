package com.freight.shipper.core.persistence.network.service

import com.freight.shipper.core.persistence.network.response.ApiResponse
import com.freight.shipper.model.User
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
    @POST("webservices")
    fun login(
        @Field("username") userName: String,
        @Field("password") password: String,
        @Field("type") type: String = "shipper",
        @Field("param") param: String = "login"
    ): Call<ApiResponse<User>>

    @FormUrlEncoded
    @POST("webservices")
    fun signupAsCompany(
        @Field("first_name") firstName: String,
        @Field("last_name") lastName: String,
        @Field("email") email: String,
        @Field("phone") phone: String,
        @Field("address") address: String,
        @Field("country_id") country: String,
        @Field("state") state: String,
        @Field("city") city: String,
        @Field("postcode") postcode: String,
        @Field("password") password: String,
        @Field("company_name") companyName: String,
        @Field("param") param: String = "customerRegister"
    ): Call<ApiResponse<User>>

    @FormUrlEncoded
    @POST("webservices")
    fun forgotPassword(
        @Field("email") email: String,
        @Field("param") param: String = "forgotPassword"
    ): Call<ApiResponse<User>>
}