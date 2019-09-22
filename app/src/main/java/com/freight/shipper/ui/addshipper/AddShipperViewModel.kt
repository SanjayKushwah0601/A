package com.freight.shipper.ui.addshipper

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.freight.shipper.core.persistence.network.request.AddShipperRequest
import com.freight.shipper.core.platform.BaseViewModel
import com.freight.shipper.core.persistence.network.response.Country
import com.freight.shipper.core.persistence.network.response.State
import com.freight.shipper.core.persistence.network.response.Vehicle
import com.freight.shipper.repository.ProfileRepository
import timber.log.Timber


class AddShipperViewModel(private val model: ProfileRepository) : BaseViewModel() {

    private val selectedCountry = MutableLiveData<Country>()
    var countries: LiveData<List<Country>> = model.countries
    var states: LiveData<List<State>> =
        Transformations.switchMap(selectedCountry) { model.getPickStates(it.countryId) }

    val vehicleList: LiveData<List<Vehicle>> get() = _vehicleList
    private val _vehicleList = MediatorLiveData<List<Vehicle>>()

    init {
        _vehicleList.addSource(model.vehicleList) {
            _vehicleList.postValue(it)
        }
    }

    val requestModel: AddShipperRequest by lazy { AddShipperRequest() }


    var isLoading = ObservableField<Boolean>().apply { set(false) }
    val addShipperResponse: LiveData<String> get() = _addShipperResponse
    private val _addShipperResponse = MediatorLiveData<String>()
    var error = MediatorLiveData<String>()


    fun submitShipperDetails() {
        Timber.e(requestModel.toString())
        if (validateFormField()) {
            isLoading.set(true)
            model.addNewShipper(requestModel, Pair(_addShipperResponse, error))
        }
    }


    private fun validateFormField(): Boolean {
        return if (requestModel.isAllFilled()) {
            Timber.d(requestModel.firstName)
            true
        } else {
            false
        }
    }

    fun onCountrySelect(country: Country) {
        requestModel.countryId = country.countryId
        selectedCountry.postValue(country)
    }
}