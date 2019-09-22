package com.freight.shipper.ui.driverlist

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.freight.shipper.core.persistence.network.response.Driver
import com.freight.shipper.core.persistence.network.response.Vehicle
import com.freight.shipper.core.platform.BaseViewModel
import com.freight.shipper.repository.ProfileRepository
import com.freight.shipper.ui.driverlist.recyclerview.DriverClickListener

/**
 * @CreatedBy Sanjay Kushwah on 9/22/2019.
 * sanjaykushwah0601@gmail.com
 */
class DriverViewModel(private val model: ProfileRepository) : BaseViewModel(),
    DriverClickListener {

    var hasDrivers = ObservableField<Boolean>()
    var isLoading = ObservableField<Boolean>()

    val driverList: LiveData<List<Driver>> get() = _driverList
    private val _driverList = MediatorLiveData<List<Driver>>()
    val driverListError: LiveData<String> get() = _driverListError
    private val _driverListError = MediatorLiveData<String>()

    init {
        _driverListError.addSource(model.driverListError) {
            _driverListError.postValue(it)
            hasDrivers.set(false)
            isLoading.set(false)
        }
        _driverList.addSource(model.driverList) {
            _driverList.postValue(it)
            hasDrivers.set(it.isNotEmpty())
            isLoading.set(false)
        }
    }

    fun refreshDriverList() {
        model.fetchDriverList()
        isLoading.set(true)
    }

    // region - VehicleClickListener interface method
    override fun onDriverClick(position: Int, load: Driver) {
    }
    // endregion
}
