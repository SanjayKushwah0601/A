package com.freight.shipper.ui.authentication.signup.company

import com.freight.shipper.core.persistence.network.client.server.MeuralAPIContract
import com.freight.shipper.core.persistence.network.result.APIResult
import com.freight.shipper.core.persistence.preference.LoginManager
import com.freight.shipper.model.User
import com.freight.shipper.ui.authentication.signup.CompanySignup


/**
 * @CreatedBy Sanjay Kushwah on 8/4/2019.
 * sanjaykushwah0601@gmail.com
 */
class CompanySignupModel(
    private val api: MeuralAPIContract,
    private val loginManager: LoginManager
) {

    suspend fun companySignup(model: CompanySignup): APIResult<User> {
        return api.signupAsCompany(model)
    }

    fun saveLoginUser(user: User) {
        loginManager.loggedInUser = user
    }

    fun saveToken(token: String) {
        loginManager.setToken(token)
    }
}