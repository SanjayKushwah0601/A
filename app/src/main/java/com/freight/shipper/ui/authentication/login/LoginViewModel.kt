package com.freight.shipper.ui.authentication.login

import com.freight.shipper.core.persistence.network.dispatchers.DispatcherProvider
import com.freight.shipper.core.persistence.network.dispatchers.DispatcherProviderImpl
import com.freight.shipper.core.persistence.network.result.APIResult
import com.freight.shipper.core.platform.BaseViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class LoginViewModel(
    private val model: LoginModel,
    val dispatcher: DispatcherProvider = DispatcherProviderImpl()
) : BaseViewModel() {

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
}
