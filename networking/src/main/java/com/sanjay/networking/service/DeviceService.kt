package com.sanjay.networking.service

import com.sanjay.networking.client.DeclineNullValue
import com.sanjay.networking.constraint.Sort
import com.sanjay.networking.response.ApiResponse
import com.sanjay.networking.response.model.*
import retrofit2.Call
import retrofit2.http.*

/**
 * Contract for Canvas-related REST API calls
 *
 * A Retrofit object will create the actual implementation of this Service
 */

interface DeviceService {
    @GET("/v0/devices/{deviceId}")
    fun getCanvas(@Path("deviceId") deviceId: Long): Call<ApiResponse<Device>>

    @DeclineNullValue
    @PUT("/v0/devices/{deviceId}")
    fun updateCanvas(@Path("deviceId") deviceId: Long, @Body newSettings: DeviceOptions): Call<ApiResponse<Device>>

    @DELETE("/v0/devices/{deviceId}")
    fun deleteCanvas(@Path("deviceId") deviceId: Long): Call<ApiResponse<Unit>>

    @GET("/v0/devices/{deviceId}/galleries")
    fun getDeviceGalleries(@Path("deviceId") deviceId: Long, @Query("page") page: Int = 1, @Query("count") count: Int = Int.MAX_VALUE, @Query("sort") sort: Sort? = null): Call<ApiResponse<List<Gallery>>>

    @POST("/v0/devices/{deviceId}/galleries/{galleryId}")
    fun addGalleryToCanvas(@Path("deviceId") deviceId: Long, @Path("galleryId") galleryId: Long): Call<ApiResponse<Device>>

    @DELETE("/v0/devices/{deviceId}/galleries/{galleryId}")
    fun deleteGalleryFromCanvas(@Path("deviceId") deviceId: Long, @Path("galleryId") galleryId: Long): Call<ApiResponse<Device>>

    @POST("/v0/devices/{deviceId}/preview/{itemId}")
    fun previewItemOnCanvas(@Path("deviceId") deviceId: Long, @Path("itemId") itemId: Long): Call<ApiResponse<Unit>>

    @POST("/v0/devices/{deviceId}/sync")
    fun syncCanvas(@Path("deviceId") deviceId: Long): Call<ApiResponse<String>>

    @FormUrlEncoded
    @PUT("/v0/devices/{deviceId}")
    fun setSchedulerEnabled(@Path("deviceId") deviceId: Long, @Field("schedulerEnabled") schedulerEnabled: Boolean): Call<ApiResponse<Device>>

    @POST("v0/devices/{deviceId}/control")
    fun sendCommand(@Path("deviceId") deviceId: Long, @Body command: DeviceCommand): Call<ApiResponse<Unit>>

    @GET("/v0/devices/{deviceId}/items")
    fun getAllWorks(@Path("deviceId") deviceId: Long, @Query("page") page: Int? = 1, @Query("count") count: Int? = Int.MAX_VALUE, @Query("sort") sort: Sort? = null): Call<ApiResponse<List<Image>>>

    @POST("v0/devices/{deviceId}/items/{itemId}")
    fun addAllWorksItem(@Path("deviceId") deviceId: Long, @Path("itemId") itemId: Long): Call<ApiResponse<Device>>

    @DELETE("/v0/devices/{deviceId}/items/{itemId}")
    fun removeAllWorksItem(@Path("deviceId") deviceId: Long, @Path("itemId") workId: Long): Call<ApiResponse<Device>>

    @GET("/v0/devices/display_languages")
    fun getDeviceLanguages(): Call<ApiResponse<List<Language>>>

}