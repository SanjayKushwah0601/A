package com.freight.shipper.core.persistence.network.service

import com.freight.shipper.core.persistence.network.response.ApiResponse
import com.freight.shipper.core.persistence.network.response.EmptyResponse
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
interface ProfileService {
    @FormUrlEncoded
    @POST("webservices")
    fun addShipperPaymentDetail(
        @Field("bank_acc_no") accountNumber: String,
        @Field("bank_name") bankName: String,
        @Field("bank_address") bankAddress: String,
        @Field("wire_transfer_num") wireTransferNumber: String,
        @Field("currency") currency: String,
        @Field("param") param: String = "addShipperPaymentDetail"
    ): Call<ApiResponse<Any>>

    @POST("webservices")
    fun addVehicle(@Body file: RequestBody)
            : Call<ApiResponse<EmptyResponse>>

    @FormUrlEncoded
    @POST("webservices")
    fun addNewShipper(
        @Field("first_name") firstName: String,
        @Field("last_name") lastName: String,
        @Field("email") email: String,
        @Field("phone") phone: String,
        @Field("country_id") countryId: String,
        @Field("state") state: String,
        @Field("city") city: String,
        @Field("postcode") postcode: String,
        @Field("address") address: String,
        @Field("password") password: String,
        @Field("param") param: String = "addDriver"
    ): Call<ApiResponse<EmptyResponse>>
}