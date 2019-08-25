package com.freight.shipper.core.persistence.network.client.server

import com.freight.shipper.core.persistence.network.request.PaymentRequest
import com.freight.shipper.core.persistence.network.result.APIResult
import com.freight.shipper.model.*
import com.freight.shipper.ui.authentication.signup.CompanySignup

abstract class APIContract {

    companion object {
        private const val MAX_PAGE_SIZE = 10
    }

    // region - Auth
    abstract suspend fun login(email: String, password: String): APIResult<User>

    abstract suspend fun signupAsCompany(model: CompanySignup): APIResult<User>
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
    // endregion

    // region - Profile
    abstract suspend fun addShipperPaymentDetail(paymentRequest: PaymentRequest): APIResult<Any>
    // endregion

    // region - Category
    abstract suspend fun getCategory(id: Long): APIResult<Category>
    // endregion
}