package com.freight.shipper.ui.profile

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.freight.shipper.repository.ProfileRepository

class ProfileViewModel(model: ProfileRepository) : ViewModel() {

    var isLoading = ObservableField<Boolean>().apply { set(false) }
    val user = model.getUserProfile()
}

