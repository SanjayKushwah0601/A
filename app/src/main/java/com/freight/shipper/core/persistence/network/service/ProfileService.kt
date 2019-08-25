package com.freight.shipper.core.persistence.network.service

import com.freight.shipper.core.persistence.network.response.ApiResponse
import retrofit2.Call
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
}