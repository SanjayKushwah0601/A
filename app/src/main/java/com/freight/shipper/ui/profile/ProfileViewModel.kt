package com.freight.shipper.ui.profile

import androidx.lifecycle.ViewModel
import com.freight.shipper.repository.ProfileRepository

class ProfileViewModel(model: ProfileRepository) : ViewModel() {

    val user = model.getUserProfile()
}

