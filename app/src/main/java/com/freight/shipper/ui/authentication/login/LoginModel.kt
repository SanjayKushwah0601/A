package com.freight.shipper.ui.authentication.login

import com.freight.shipper.core.persistence.network.client.server.MeuralAPIContract
import com.freight.shipper.core.persistence.network.result.APIResult
import com.freight.shipper.core.persistence.preference.LoginManager
import com.freight.shipper.model.User


/**
 * @CreatedBy Sanjay Kushwah on 8/4/2019.
 * sanjaykushwah0601@gmail.com
 */
class LoginModel(
    private val api: MeuralAPIContract,
    private val loginManager: LoginManager
) {

    suspend fun login(email: String, password: String): APIResult<User> {
        return api.login(email, password)
    }

    fun saveLoginUser(user: User) {
        loginManager.loggedInUser = user
    }

    fun saveToken(token: String) {
        loginManager.setToken(token)
    }
}