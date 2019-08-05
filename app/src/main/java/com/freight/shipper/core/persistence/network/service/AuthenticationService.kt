package com.freight.shipper.core.persistence.network.service

import com.freight.shipper.core.persistence.network.response.ApiResponse
import com.freight.shipper.model.User
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
    @POST("webservices")
    fun login(
        @Field("username") userName: String,
        @Field("password") password: String,
        @Field("type") type: String = "shipper",
        @Field("param") param: String = "login"
    ): Call<ApiResponse<User>>

//    param - customerRegister
//first_name=Customer&last_name=Five&email=customerfive@test.com&
// phone=3464564576&country_id=2&state=South Australia&city=Moore Park&
// postcode=3221&password=123&company_name=My Test Company&address=Church get

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
}