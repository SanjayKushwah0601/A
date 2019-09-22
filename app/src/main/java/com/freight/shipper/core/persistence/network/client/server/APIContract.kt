package com.freight.shipper.core.persistence.network.client.server

import com.freight.shipper.core.persistence.network.request.AddShipperRequest
import com.freight.shipper.core.persistence.network.request.PaymentRequest
import com.freight.shipper.core.persistence.network.response.*
import com.freight.shipper.core.persistence.network.result.APIResult
import com.freight.shipper.ui.authentication.signup.CompanySignup
import okhttp3.MultipartBody

abstract class APIContract {

    companion object {
        private const val MAX_PAGE_SIZE = 10
    }

    // region - Auth
    abstract suspend fun login(email: String, password: String): APIResult<User>

    abstract suspend fun signupAsCompany(model: CompanySignup): APIResult<User>
    abstract suspend fun signupIndividual(model: CompanySignup): APIResult<User>
    abstract suspend fun forgotPassword(email: String): APIResult<User>

    abstract suspend fun register(
        firstName: String, lastName: String, email: String,
        password: String, confirmPassword: String, countryCode: String,
        isReceiveCommunications: Boolean, isSecurityToken: Boolean
    ): APIResult<Token>

    abstract suspend fun getMasterConfigData(): APIResult<MasterConfig>
    // endregion

    // region - Load
    abstract suspend fun getLoad(pickDate: String?): APIResult<List<ActiveLoad>>

    abstract suspend fun getNewLoad(date: String?): APIResult<List<NewLoad>>
    abstract suspend fun acceptLoad(loadId: String): APIResult<EmptyResponse>
    abstract suspend fun addLoadOfferPrice(
        loadId: String, offerPrice: String
    ): APIResult<EmptyResponse>
    // endregion

    // region - Profile
    abstract suspend fun addShipperPaymentDetail(paymentRequest: PaymentRequest): APIResult<Any>

    abstract suspend fun addVehicle(requestBody: MultipartBody): APIResult<EmptyResponse>
    abstract suspend fun addNewShipper(request: AddShipperRequest): APIResult<EmptyResponse>
    abstract suspend fun getVehicleList(): APIResult<List<Vehicle>>
    abstract suspend fun getDriverList(): APIResult<List<Driver>>
    // endregion

    // region - Category
    abstract suspend fun getCategory(id: Long): APIResult<Category>
    // endregion
}