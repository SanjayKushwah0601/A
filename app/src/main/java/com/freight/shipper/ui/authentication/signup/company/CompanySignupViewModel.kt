package com.freight.shipper.ui.authentication.signup.company

import com.freight.shipper.core.persistence.network.dispatchers.DispatcherProvider
import com.freight.shipper.core.persistence.network.dispatchers.DispatcherProviderImpl
import com.freight.shipper.core.persistence.network.result.APIResult
import com.freight.shipper.core.platform.ActionLiveData
import com.freight.shipper.core.platform.BaseViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class CompanySignupViewModel(
    private val model: CompanySignupModel,
    val dispatcher: DispatcherProvider = DispatcherProviderImpl()
) : BaseViewModel() {

    val nextButtonAction = ActionLiveData<Boolean>()
    val resetPasswordAction = ActionLiveData<Boolean>()

    fun login() {
        GlobalScope.launch(dispatcher.io) {
            val result = model.login()
            withContext(dispatcher.main) {
                when (result) {
                    is APIResult.Success -> Timber.e("Success Sanjay: ${result.response}")
                    is APIResult.Failure -> Timber.e("Failure Sanjay: ${result.error}")
                }
            }
        }
    }

    fun onNextButtonClicked() {
        nextButtonAction.sendAction(true)
    }

    fun onResetButtonClicked() {
        resetPasswordAction.sendAction(true)
    }
}
