package com.freight.shipper.ui.vehiclelist

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.freight.shipper.core.persistence.network.response.Vehicle
import com.freight.shipper.core.platform.BaseViewModel
import com.freight.shipper.repository.ProfileRepository
import com.freight.shipper.ui.vehiclelist.recyclerview.VehicleClickListener

/**
 * @CreatedBy Sanjay Kushwah on 9/22/2019.
 * sanjaykushwah0601@gmail.com
 */
class VehicleViewModel(private val model: ProfileRepository) : BaseViewModel(),
    VehicleClickListener {

    var hasVehicles = ObservableField<Boolean>()
    var isLoading = ObservableField<Boolean>()

    val vehicleList: LiveData<List<Vehicle>> get() = _vehicleList
    private val _vehicleList = MediatorLiveData<List<Vehicle>>()
    val vehicleListError: LiveData<String> get() = _vehicleListError
    private val _vehicleListError = MediatorLiveData<String>()

    init {
        _vehicleListError.addSource(model.vehicleListError) {
            _vehicleListError.postValue(it)
            hasVehicles.set(false)
            isLoading.set(false)
        }
        _vehicleList.addSource(model.vehicleList) {
            _vehicleList.postValue(it)
            hasVehicles.set(it.isNotEmpty())
            isLoading.set(false)
        }
    }


    fun refreshVehicleList() {
        model.fetchVehicleList()
        isLoading.set(true)
    }

    // region - VehicleClickListener interface method
    override fun onVehicleClick(position: Int, load: Vehicle) {
    }
    // endregion
}
