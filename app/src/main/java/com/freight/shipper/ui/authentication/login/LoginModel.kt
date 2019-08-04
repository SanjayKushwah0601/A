package com.freight.shipper.ui.authentication.login

import com.freight.shipper.core.persistence.network.client.server.MeuralAPIContract
import com.freight.shipper.core.persistence.network.result.APIResult
import com.freight.shipper.model.Login


/**
 * @CreatedBy Sanjay Kushwah on 8/4/2019.
 * sanjaykushwah0601@gmail.com
 */
class LoginModel(private val api: MeuralAPIContract) {

    suspend fun login(): APIResult<Login> {
        return api.login("indshipper6@test.com", "123")
    }
}