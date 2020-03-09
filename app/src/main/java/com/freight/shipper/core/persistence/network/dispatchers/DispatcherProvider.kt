package com.freight.shipper.core.persistence.network.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

/**
 *
 * [DispatcherProvider] provides the dispatcher to make sure test case should run properly
 *
 * @author Sanjay Kushwah
 */
interface DispatcherProvider {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
}