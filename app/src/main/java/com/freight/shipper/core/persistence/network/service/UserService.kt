package com.freight.shipper.core.persistence.network.service

import com.freight.shipper.core.persistence.network.request.UserFeedback
import com.freight.shipper.core.persistence.network.response.ApiResponse
import com.freight.shipper.model.Category
import com.freight.shipper.model.Token
import com.freight.shipper.model.User
import retrofit2.Call
import retrofit2.http.*

/**
 * Contract for User-related REST api calls
 *
 * A Retrofit object will create the actual implementation of this Service
 */

interface UserService {


    //Create a User (Registration)
    //No Auth Token Required
    @FormUrlEncoded
    @POST("/v0/user")
    fun createUser(
        @Field("firstName") firstName: String,
        @Field("lastName") lastName: String,
        @Field("email") email: String,
        @Field("password1") password: String,
        @Field("password2") confirmPassword: String,
        @Field("country") countryCode: String,
        @Field("receiveCommunications") isReceiveCommunications: Boolean,
        @Field("securityToken") isSecurityToken: Boolean
    ): Call<ApiResponse<Token>>


    //Get the current logged in user
    //Auth token required in header
    @GET("/v0/user")
    fun getCurrentUser()
            : Call<ApiResponse<User>>

    //Update the current logged in user's email or username
    @FormUrlEncoded
    @PUT("/v0/user")
    fun updateUser(
        @Field("email") email: String?,
        @Field("username") username: String?
    )
            : Call<ApiResponse<User>>

    @GET("/v0/favorites/categories/")
    fun getFavoriteCategories(@Query("page") pageNumber: Int = 0, @Query("count") count: Int = Int.MAX_VALUE)
            : Call<ApiResponse<List<Category>>>

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "/v0/favorites", hasBody = true)
    fun unfavorite(@Field("id") id: Long, @Field("model") model: String)
            : Call<ApiResponse<Unit>>

    @POST("/v0/user/feedback")
    fun submitFeedBack(@Body userFeedback: UserFeedback): Call<ApiResponse<Unit>>
}