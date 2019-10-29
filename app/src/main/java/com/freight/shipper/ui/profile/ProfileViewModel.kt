package com.freight.shipper.ui.profile

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.freight.shipper.core.persistence.network.response.Country
import com.freight.shipper.core.persistence.network.response.State
import com.freight.shipper.core.platform.BaseViewModel
import com.freight.shipper.repository.ProfileRepository

class ProfileViewModel(val model: ProfileRepository) : BaseViewModel() {

    var countries: LiveData<List<Country>> = model.countries
    var isLoading = ObservableField<Boolean>().apply { set(false) }
    val user = model.getUserProfile()

    var error = MediatorLiveData<String>()

    val updateProfileResponse: LiveData<String>
        get() = _updateProfileResponse

    private val _updateProfileResponse = MediatorLiveData<String>()

    private val selectedCountry = MutableLiveData<Country>()
    var states: LiveData<List<State>> =
        Transformations.switchMap(selectedCountry) { model.getPickStates(it.countryId) }

    fun getPickStates(selectedCountry: Country) {
        this.selectedCountry.postValue(selectedCountry)
    }

    fun updateProfile() {
        isLoading.set(true)
        model.updateProfile(user, Pair(_updateProfileResponse, error))
    }
}

