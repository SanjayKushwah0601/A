package com.freight.shipper.ui.authentication.signup.company

import com.freight.shipper.core.persistence.network.client.server.MeuralAPIContract
import com.freight.shipper.core.persistence.network.result.APIResult
import com.freight.shipper.model.User
import com.freight.shipper.ui.authentication.signup.CompanySignup


/**
 * @CreatedBy Sanjay Kushwah on 8/4/2019.
 * sanjaykushwah0601@gmail.com
 */
class CompanySignupModel(private val api: MeuralAPIContract) {

    suspend fun companySignup(model: CompanySignup): APIResult<User> {
        return api.signupAsCompany(model)
    }
}