package com.freight.shipper.repository

import androidx.lifecycle.MediatorLiveData
import com.freight.shipper.core.persistence.network.client.server.APIContract
import com.freight.shipper.core.persistence.network.dispatchers.DispatcherProvider
import com.freight.shipper.core.persistence.network.dispatchers.DispatcherProviderImpl
import com.freight.shipper.core.persistence.network.result.APIResult
import com.freight.shipper.core.persistence.preference.LoginManager
import com.freight.shipper.extensions.BaseRepository
import com.freight.shipper.model.ActiveLoad
import com.freight.shipper.ui.profile.paymentdetails.PaymentRequest
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber


/**
 * @CreatedBy Sanjay Kushwah on 8/4/2019.
 * sanjaykushwah0601@gmail.com
 */
class ProfileRepository(
    private val api: APIContract,
    private val loginManager: LoginManager,
    val dispatcher: DispatcherProvider = DispatcherProviderImpl()
) : BaseRepository() {

    fun savePaymentDetails(paymentRequest: PaymentRequest, observer: Pair<MediatorLiveData<String>, MediatorLiveData<String>>) {
        val (success, failure) = setupObserver(observer)
        GlobalScope.launch(dispatcher.io) {
            val result = api.addShipperPaymentDetail(paymentRequest)
            withContext(dispatcher.main) {
                when (result) {
                    is APIResult.Success -> {
                        Timber.e("Success Sanjay: ${result.response}")
                        success.value = result.response.data.toString()
                    }
                    is APIResult.Failure -> {
                        Timber.e("Failure Sanjay: ${result.error}")
                        failure.value = result.error?.message ?: ""
                    }
                }
            }
        }
    }

}