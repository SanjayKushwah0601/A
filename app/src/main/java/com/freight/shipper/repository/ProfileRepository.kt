package com.freight.shipper.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.freight.shipper.R
import com.freight.shipper.core.persistence.db.RoomDb
import com.freight.shipper.core.persistence.network.client.server.APIContract
import com.freight.shipper.core.persistence.network.dispatchers.DispatcherProvider
import com.freight.shipper.core.persistence.network.dispatchers.DispatcherProviderImpl
import com.freight.shipper.core.persistence.network.request.AddShipperRequest
import com.freight.shipper.core.persistence.network.request.PaymentRequest
import com.freight.shipper.core.persistence.network.response.*
import com.freight.shipper.core.persistence.network.result.APIResult
import com.freight.shipper.core.persistence.preference.LoginManager
import com.freight.shipper.extensions.BaseRepository
import com.freight.shipper.utils.StringUtil.getString
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

    private val _vehicleList = MutableLiveData<List<Vehicle>>()
    val vehicleList: LiveData<List<Vehicle>>
        get() {
            fetchVehicleList()
            return _vehicleList
        }

    private val _vehicleListError = MutableLiveData<String>()
    val vehicleListError: LiveData<String> get() = _vehicleListError


    private val _driverList = MutableLiveData<List<Driver>>()
    val driverList: LiveData<List<Driver>> get() = _driverList

    private val _driverListError = MutableLiveData<String>()
    val driverListError: LiveData<String> get() = _driverListError

    init {
        GlobalScope.launch(dispatcher.io) {
            vehicleTypes.postValue(RoomDb.instance.configDao().getConfig().vehicleTypes)
        }
    }

    fun fetchVehicleList() {
        GlobalScope.launch(dispatcher.io) {
            val result: APIResult<List<Vehicle>> = api.getVehicleList()
            withContext(dispatcher.main) {
                when (result) {
                    is APIResult.Success ->
                        _vehicleList.postValue(result.response.data)

                    is APIResult.Failure ->
                        _vehicleListError.postValue(result.error?.message ?: "")
                }
            }
        }
    }

    fun fetchDriverList() {
        GlobalScope.launch(dispatcher.io) {
            val result = api.getDriverList()
            withContext(dispatcher.main) {
                when (result) {
                    is APIResult.Success ->
                        _driverList.postValue(result.response.data)

                    is APIResult.Failure ->
                        _driverListError.postValue(result.error?.message ?: "")
                }
            }
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

    fun updateProfile(
        user: User,
        observer: Pair<MediatorLiveData<String>, MediatorLiveData<String>>
    ) {
        val (success, failure) = setupObserver(observer)
        GlobalScope.launch(dispatcher.io) {
            val result = api.updateProfile(user)
            withContext(dispatcher.main) {
                when (result) {
                    is APIResult.Success -> {
                        Timber.e("Success Sanjay: ${result.response}")
                        success.value =
                            result.response.getMessage() ?: getString(R.string.update_profile_msg)
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

    fun getUserProfile(): User {
        return loginManager.loggedInUser!!
    }

}