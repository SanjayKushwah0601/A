package com.freight.shipper.ui.authentication.signup

import com.freight.shipper.core.platform.ActionLiveData
import com.freight.shipper.core.platform.BaseViewModel

class SignupLandingViewModel : BaseViewModel() {

    val companyAction = ActionLiveData<Boolean>()
    val individualAction = ActionLiveData<Boolean>()

    fun onCompanyButtonClicked() {
        companyAction.sendAction(true)
    }

    fun onIndividualButtonClicked() {
        individualAction.sendAction(true)
    }
}
