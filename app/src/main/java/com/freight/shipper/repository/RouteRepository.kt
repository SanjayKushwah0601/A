package com.freight.shipper.repository

import com.freight.shipper.core.persistence.network.client.server.APIContract
import com.freight.shipper.core.persistence.network.dispatchers.DispatcherProvider
import com.freight.shipper.core.persistence.network.dispatchers.DispatcherProviderImpl
import com.freight.shipper.core.persistence.preference.LoginManager


/**
 * @CreatedBy Sanjay Kushwah on 10/8/2019.
 * sanjaykushwah0601@gmail.com
 */
class RouteRepository(
    private val api: APIContract,
    private val loginManager: LoginManager,
    val dispatcher: DispatcherProvider = DispatcherProviderImpl()
) {
}