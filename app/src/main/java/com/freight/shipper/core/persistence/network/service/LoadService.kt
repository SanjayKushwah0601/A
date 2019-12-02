package com.freight.shipper.core.persistence.network.service

import com.freight.shipper.core.persistence.network.response.*
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
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

    @POST("webservices?param=getLoad&load_type=MLP")
    fun getPastLoad(): Call<ApiResponse<List<PastLoad>>>

    @FormUrlEncoded
    @POST("webservices?param=getLoad")
    fun getNewLoad(
        @Field("weight_from") weightFrom: Int?,
        @Field("weight_to") weightTo: Int?,
        @Field("distance") distance: Int?
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
        @Field("price") offerPrice: String?
    ): Call<ApiResponse<EmptyResponse>>

    @FormUrlEncoded
    @POST("webservices?param=updateLoadStatus")
    fun updateLoadStatus(
        @Field("loads_id") loadId: String,
        @Field("load_status_id") loadStatusId: Int
    ): Call<ApiResponse<EmptyResponse>>

    @FormUrlEncoded
    @POST("webservices?param=getLoadDetail")
    fun getLoadDetail(
        @Field("loads_id") loadId: String
    ): Call<ApiResponse<LoadDetail>>

    @POST("webservices?param=signLoadInovice")
    fun uploadInvoice(@Body file: RequestBody)
            : Call<ApiResponse<EmptyResponse>>
}