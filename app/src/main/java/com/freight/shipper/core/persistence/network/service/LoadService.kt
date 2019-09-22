package com.freight.shipper.core.persistence.network.service

import com.freight.shipper.core.persistence.network.response.ApiResponse
import com.freight.shipper.core.persistence.network.response.EmptyResponse
import com.freight.shipper.core.persistence.network.response.ActiveLoad
import com.freight.shipper.core.persistence.network.response.NewLoad
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


/**
 * @CreatedBy Sanjay Kushwah on 8/15/2019.
 * sanjaykushwah0601@gmail.com
 */
interface LoadService {
    @FormUrlEncoded
    @POST("webservices")
    fun getLoad(
        @Field("pick_date") pick_date: String?,
        @Field("param") param: String = "getLoad"
    ): Call<ApiResponse<List<ActiveLoad>>>

    @FormUrlEncoded
    @POST("webservices?param=getLoad")
    fun getNewLoad(
        @Field("pick_date") pick_date: String?
    ): Call<ApiResponse<List<NewLoad>>>

    @FormUrlEncoded
    @POST("webservices?param=addLoadOfferPrice")
    fun acceptLoad(
        @Field("loads_id") loadId: String?
    ): Call<ApiResponse<EmptyResponse>>

    @FormUrlEncoded
    @POST("webservices?param=addLoadOfferPrice")
    fun addLoadOfferPrice(
        @Field("loads_id") loadId: String?,
        @Field("offer_price") offerPrice: String?
    ): Call<ApiResponse<EmptyResponse>>
}