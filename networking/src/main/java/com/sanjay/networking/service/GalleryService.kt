package com.sanjay.networking.service

import com.sanjay.networking.annotations.OrientationString
import com.sanjay.networking.constraint.Filter
import com.sanjay.networking.constraint.Sort
import com.sanjay.networking.response.ApiResponse
import com.sanjay.networking.response.model.Gallery
import retrofit2.Call
import retrofit2.http.*

/**
 * Contract for Gallery-related REST API calls
 *
 * A Retrofit object will create the actual implementation of this Service
 */

interface GalleryService {
    @GET("/v0/galleries/{id}")
    fun getGallery(@Path("id") galleryId: Long): Call<ApiResponse<Gallery>>

    @FormUrlEncoded
    @POST("/v0/galleries")
    fun createGallery(@Field("name") name: String,
                      @Field("description") description: String,
                      @OrientationString
                      @Field("orientation") orientation: String?)
            : Call<ApiResponse<Gallery>>

    @FormUrlEncoded
    @PUT("/v0/galleries/{id}")
    fun updateGallery(@Path("id") galleryId: Long,
                      @Field("name") name: String?,
                      @Field("description") description: String?,
                      @OrientationString
                      @Field("orientation") orientation: String?,
                      @Field("coverId") coverId: Long?)
            : Call<ApiResponse<Gallery>>

    @DELETE("/v0/galleries/{id}")
    fun deleteGallery(@Path("id") galleryId: Long): Call<ApiResponse<Unit>>

    @FormUrlEncoded
    @PUT("/v0/galleries/{galleryId}/sort")
    fun sortGallery(@Path("galleryId") galleryId: Long, @Field("order") order: List<Long>): Call<ApiResponse<Gallery>>

    @POST("/v0/galleries/{galleryId}/items/{itemId}")
    fun addItemToGallery(@Path("galleryId") galleryId: Long, @Path("itemId") itemId: Long): Call<ApiResponse<Gallery>>

    @DELETE("/v0/galleries/{galleryId}/items/{itemId}")
    fun removeItemFromGallery(@Path("galleryId") galleryId: Long, @Path("itemId") itemId: Long): Call<ApiResponse<Unit>>

    @GET("v0/{model}/{id}/galleries")
    fun getModelGalleries(@Path("model") model: String,
                          @Path("id") id: Long,
                          @Query("page") page: Int? = 1,
                          @Query("count") count: Int? = Int.MAX_VALUE,
                          @Query("sort") sort: Sort? = null,
                          @Query("filter") filter: Filter? = null): Call<ApiResponse<List<Gallery>>>
}
