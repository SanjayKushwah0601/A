package com.freight.shipper.ui.authentication.forgotpassword

import androidx.databinding.ObservableField
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.freight.shipper.R
import com.freight.shipper.core.platform.BaseViewModel
import com.freight.shipper.core.persistence.network.response.User
import com.freight.shipper.repository.AuthenticationRepository

class ForgotPasswordViewModel(private val model: AuthenticationRepository) : BaseViewModel() {

    val email: ObservableField<String> = ObservableField()
    var isLoading = MutableLiveData<Boolean>()
    var error = MediatorLiveData<String>()
    var forgotPasswordResponse = MediatorLiveData<User>()

    fun onRequestForgotPassword() {
        val email = email.get()
        if (email.isNullOrEmpty()) {
            error.postValue(getString(R.string.emai_empty_error))
            return
        }
        isLoading.postValue(true)
        model.forgotPassword(email, Pair(forgotPasswordResponse, error))
    }
}
