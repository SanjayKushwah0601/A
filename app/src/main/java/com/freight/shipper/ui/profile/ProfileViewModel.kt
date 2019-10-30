package com.freight.shipper.ui.profile

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.freight.shipper.core.persistence.network.response.Country
import com.freight.shipper.core.persistence.network.response.State
import com.freight.shipper.core.persistence.network.response.User
import com.freight.shipper.core.platform.BaseViewModel
import com.freight.shipper.repository.ProfileRepository
import timber.log.Timber

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

    fun onAddressChanged(line2: String) {
        user.address = line2
        Timber.d(user.companyName)
    }

    fun onCityChanged(city: String) {
        user.city = city
        Timber.d(user.companyName)
    }

    fun onPostCodeChanged(postCode: String) {
        user.postalCode = postCode
        Timber.d(user.companyName)
    }

    fun onPasswordChanged(password: String) {
//        user.password = password
        Timber.d(user.companyName)
    }

    fun onFirstNameChanged(firstName: String) {
        user.firstName = firstName
        Timber.d(user.companyName)
    }

    fun onLastNameChanged(lastName: String) {
        user.lastName = lastName
        Timber.d(user.companyName)
    }

    fun onPersonEmailChanged(email: String) {
        user.email = email
        Timber.d(user.companyName)
    }

    fun onPersonMobileChanged(phone: String) {
        user.phone = phone
        Timber.d(user.companyName)
    }

    fun getUpdatedUser(): User {
        return model.getUserProfile()
    }

    fun updateProfile() {
        isLoading.set(true)
        model.updateProfile(user, Pair(_updateProfileResponse, error))
    }
}

