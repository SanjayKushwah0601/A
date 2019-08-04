package com.sanjay.networking.service

import com.sanjay.networking.annotations.ModelTypeString
import com.sanjay.networking.constraint.Sort
import com.sanjay.networking.request.UserFeedback
import com.sanjay.networking.response.ApiResponse
import com.sanjay.networking.response.model.*
import retrofit2.Call
import retrofit2.http.*

/**
 * Contract for User-related REST API calls
 *
 * A Retrofit object will create the actual implementation of this Service
 */

interface UserService {



    //Create a User (Registration)
    //No Auth Token Required
    @FormUrlEncoded
    @POST("/v0/user")
    fun createUser(@Field("firstName") firstName: String,
                   @Field("lastName") lastName: String,
                   @Field("email") email: String,
                   @Field("password1") password: String,
                   @Field("password2") confirmPassword: String,
                   @Field("country") countryCode: String,
                   @Field("receiveCommunications") isReceiveCommunications: Boolean,
                   @Field("securityToken") isSecurityToken: Boolean): Call<ApiResponse<Token>>


    //Get the current logged in user
    //Auth token required in header
    @GET("/v0/user")
    fun getCurrentUser()
            : Call<ApiResponse<User>>

    //Update the current logged in user's email or username
    @FormUrlEncoded
    @PUT("/v0/user")
    fun updateUser(@Field("email") email: String?,
                   @Field("username") username: String?)
            : Call<ApiResponse<User>>

    @GET("/v0/user/devices/")
    fun getUserCanvases(@Query("page") pageNumber: Int = 1,
                        @Query("count") count: Int = Int.MAX_VALUE)
            : Call<ApiResponse<List<Device>>>

    @FormUrlEncoded
    @POST("/v0/devices/")
    fun addUserCanvas(@Field("productKey") productKey: String)
            : Call<ApiResponse<Device>>

    @GET("/v0/user/items/")
    fun getUserImages(@Query("page") pageNumber: Int = 0, @Query("count") count: Int = Int.MAX_VALUE)
            : Call<ApiResponse<List<Image>>>

    @GET("/v0/user/galleries/")
    fun getUserGalleries(@Query("page") pageNumber: Int = 1, @Query("count") count: Int = Int.MAX_VALUE, @Query("sort") sort: Sort? = null)
            : Call<ApiResponse<List<Gallery>>>

    @GET("/v0/favorites/items/")
    fun getFavoriteItems(@Query("page") pageNumber: Int = 0, @Query("count") count: Int = Int.MAX_VALUE)
            : Call<ApiResponse<List<Image>>>

    @GET("/v0/favorites/galleries/")
    fun getFavoriteGalleries(@Query("page") pageNumber: Int = 0, @Query("count") count: Int = Int.MAX_VALUE)
            : Call<ApiResponse<List<Gallery>>>

    @GET("/v0/favorites/artists/")
    fun getFavoriteArtists(@Query("page") pageNumber: Int = 0, @Query("count") count: Int = Int.MAX_VALUE)
            : Call<ApiResponse<List<Artist>>>

    @GET("/v0/favorites/channels/")
    fun getFavoriteChannels(@Query("page") pageNumber: Int = 0, @Query("count") count: Int = Int.MAX_VALUE)
            : Call<ApiResponse<List<Channel>>>

    @GET("/v0/favorites/categories/")
    fun getFavoriteCategories(@Query("page") pageNumber: Int = 0, @Query("count") count: Int = Int.MAX_VALUE)
            : Call<ApiResponse<List<Category>>>

    @FormUrlEncoded
    @POST("/v0/favorites")
    fun favorite(@ModelTypeString
                 @Field("model") model: String,
                 @Field("id") id: Long)
            : Call<ApiResponse<Favorite>>

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "/v0/favorites", hasBody = true)
    fun unfavorite(@Field("id") id: Long, @Field("model") model: String)
            : Call<ApiResponse<Unit>>

    @POST("/v0/user/feedback")
    fun submitFeedBack(@Body userFeedback: UserFeedback): Call<ApiResponse<Unit>>

    @GET("/v0/user/purchases/items")
    fun getUserPurchasedWorks(): Call<ApiResponse<List<Image>>>

    @GET("/v0/user/purchases/galleries")
    fun getUserPurchasedGalleries(): Call<ApiResponse<List<Gallery>>>

    @POST("/v0/commerce/purchases/galleries/{id}")
    fun purchaseGallery(@Path("id") id: Long): Call<ApiResponse<Purchase<Gallery>>>

    @POST("/v0/commerce/purchases/items/{id}")
    fun purchaseImage(@Path("id") id: Long): Call<ApiResponse<Purchase<Image>>>
}