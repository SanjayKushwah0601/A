package com.freight.shipper.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.freight.shipper.core.persistence.db.RoomDb
import com.freight.shipper.core.persistence.network.client.server.APIContract
import com.freight.shipper.core.persistence.network.dispatchers.DispatcherProvider
import com.freight.shipper.core.persistence.network.dispatchers.DispatcherProviderImpl
import com.freight.shipper.core.persistence.network.request.AddShipperRequest
import com.freight.shipper.core.persistence.network.request.PaymentRequest
import com.freight.shipper.core.persistence.network.result.APIResult
import com.freight.shipper.core.persistence.preference.LoginManager
import com.freight.shipper.extensions.BaseRepository
import com.freight.shipper.model.State
import com.freight.shipper.model.VehicleType
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

    val vehicleTypes by lazy { MutableLiveData<List<VehicleType>>() }
    val countries by lazy { RoomDb.instance.countryDao().getCountriesLiveData() }

    init {
        GlobalScope.launch(dispatcher.io) {
            vehicleTypes.postValue(RoomDb.instance.configDao().getConfig().vehicleTypes)
        }
    }

    fun getPickStates(countryId: String): LiveData<List<State>> {
        return RoomDb.instance.countryDao().getStateLiveData(countryId)
    }

    fun savePaymentDetails(
        paymentRequest: PaymentRequest,
        observer: Pair<MediatorLiveData<String>, MediatorLiveData<String>>
    ) {
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

    fun addNewShipper(
        requestModel: AddShipperRequest,
        observer: Pair<MediatorLiveData<String>, MediatorLiveData<String>>
    ) {
        val (success, failure) = setupObserver(observer)
        GlobalScope.launch(dispatcher.io) {
            val result = api.addNewShipper(requestModel)
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