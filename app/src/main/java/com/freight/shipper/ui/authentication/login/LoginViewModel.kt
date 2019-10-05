package com.freight.shipper.ui.authentication.login

import com.freight.shipper.R
import com.freight.shipper.core.persistence.network.dispatchers.DispatcherProvider
import com.freight.shipper.core.persistence.network.dispatchers.DispatcherProviderImpl
import com.freight.shipper.core.persistence.network.result.APIResult
import com.freight.shipper.core.platform.ActionLiveData
import com.freight.shipper.core.platform.BaseViewModel
import com.freight.shipper.repository.AuthenticationRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class LoginViewModel(
    private val model: AuthenticationRepository,
    val dispatcher: DispatcherProvider = DispatcherProviderImpl()
) : BaseViewModel() {

    val signupAction = ActionLiveData<Boolean>()
    val resetPasswordAction = ActionLiveData<Boolean>()
    val loginStatusAction = ActionLiveData<Boolean>()
    val errorAction = ActionLiveData<String>()
    private var loginEmail: String? = null
    private var loginPassword: String? = null

    fun getMasterConfig() {
        model.getMasterConfigData()
    }

    fun onEmailChanged(email: String) {
        loginEmail = email
    }

    fun onPasswordChanged(password: String) {
        loginPassword = password
    }

    fun login() {
        if (loginEmail.isNullOrEmpty() || loginPassword.isNullOrEmpty()) {
            errorAction.sendAction(getString(R.string.username_or_password_empty_error))
            return
        }
        GlobalScope.launch(dispatcher.io) {
            val result = model.login(loginEmail!!, loginPassword!!)
            withContext(dispatcher.main) {
                when (result) {
                    is APIResult.Success -> {
                        errorAction.sendAction("User Success ${result.response.getMessage()}")
                        model.saveLoginUser(result.response.data)
                        model.saveToken(result.response.data.sessionToken)
                        loginStatusAction.sendAction(true)
                        Timber.e("Success Sanjay: ${result.response}")
                    }
                    is APIResult.Failure -> {
                        errorAction.sendAction(result.error?.message ?: "User Failed")
                        Timber.e("Failure Sanjay: ${result.error}")
                    }
                }
            }
        }
    }

    fun onSignupButtonClicked() {
        signupAction.sendAction(true)
    }

    fun onResetButtonClicked() {
        resetPasswordAction.sendAction(true)
    }
}
