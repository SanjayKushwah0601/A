package com.sanjay.networking.service

import com.sanjay.networking.constraint.Filter
import com.sanjay.networking.constraint.Sort
import com.sanjay.networking.response.ApiResponse
import com.sanjay.networking.response.model.CropPoints
import com.sanjay.networking.response.model.Image
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Contract for Image (a.k.a., Item)-related REST API calls
 *
 * A Retrofit object will create the actual implementation of this Service
 */

interface ImageService {
    @GET("/v0/items/{itemId}")
    fun getImage(@Path("itemId") id: Long): Call<ApiResponse<Image>>

    @Multipart
    @POST("/v0/items")
    fun uploadImage(@Part image: MultipartBody.Part): Call<ApiResponse<Image>>

    @FormUrlEncoded
    @PUT("/v0/items/{itemId}")
    fun updateImageData(@Path("itemId") itemId: Long,
                        @Field("name") name: String?,
                        @Field("author") author: String?,
                        @Field("description") description: String?,
                        @Field("medium") medium: String?,
                        @Field("year") year: String?)
            : Call<ApiResponse<Image>>

    @DELETE("/v0/items/{itemId}")
    fun deleteImage(@Path("itemId") itemId: Long): Call<ApiResponse<Unit>>

    @PUT("/v0/items/{itemId}/crop")
    fun cropImage(@Path("itemId") itemId: Long,
                  @Body cropPoints: CropPoints)
            : Call<ApiResponse<Image>>

    @GET("v0/{model}/{id}/items")
    fun getModelItems(@Path("model") model: String,
                      @Path("id") id: Long,
                      @Query("page") page: Int? = 1,
                      @Query("count") count: Int? = Int.MAX_VALUE,
                      @Query("sort") sort: Sort?,
                      @Query("filter") filter: Filter?): Call<ApiResponse<List<Image>>>
}