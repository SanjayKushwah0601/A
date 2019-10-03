package com.freight.shipper.core.persistence.network.service

import com.freight.shipper.core.persistence.network.response.*
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


/**
 * @CreatedBy Sanjay Kushwah on 8/15/2019.
 * sanjaykushwah0601@gmail.com
 */
interface LoadService {

    @POST("webservices?param=getLoad&load_type=MAL")
    fun getActiveLoad(): Call<ApiResponse<List<ActiveLoad>>>

    @POST("webservices?param=getLoad&load_type=MLCC")
    fun getPastLoad(): Call<ApiResponse<List<PastLoad>>>

    @POST("webservices?param=getLoad")
    fun getNewLoad(): Call<ApiResponse<List<NewLoad>>>

    @FormUrlEncoded
    @POST("webservices?param=addLoadOfferPrice")
    fun acceptLoad(
        @Field("loads_id") loadId: String?
    ): Call<ApiResponse<EmptyResponse>>

    @FormUrlEncoded
    @POST("webservices?param=addLoadOfferPrice")
    fun addLoadOfferPrice(
        @Field("loads_id") loadId: String?,
        @Field("price") offerPrice: String?
    ): Call<ApiResponse<EmptyResponse>>
}