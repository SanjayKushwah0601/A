package com.freight.shipper.ui.authentication.login

import com.freight.shipper.core.persistence.network.dispatchers.DispatcherProvider
import com.freight.shipper.core.persistence.network.dispatchers.DispatcherProviderImpl
import com.freight.shipper.core.persistence.network.result.APIResult
import com.freight.shipper.core.platform.ActionLiveData
import com.freight.shipper.core.platform.BaseViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class LoginViewModel(
    private val model: LoginModel,
    val dispatcher: DispatcherProvider = DispatcherProviderImpl()
) : BaseViewModel() {

    val signupAction = ActionLiveData<Boolean>()
    val resetPasswordAction = ActionLiveData<Boolean>()
    val errorAction = ActionLiveData<String>()
    private var loginEmail: String? = null
    private var loginPassword: String? = null

    fun onEmailChanged(email: String) {
        loginEmail = email
    }


    fun onPasswordChanged(password: String) {
        loginPassword = password
    }

    fun login() {
        if (loginEmail.isNullOrEmpty() && loginPassword.isNullOrEmpty()) {
            errorAction.sendAction("Username or Password is empty")
            return
        }
        GlobalScope.launch(dispatcher.io) {
            val result = model.login(loginEmail!!, loginPassword!!)
            withContext(dispatcher.main) {
                when (result) {
                    is APIResult.Success -> {
                        errorAction.sendAction("Login Success ${result.response.getMessage()}")
                        Timber.e("Success Sanjay: ${result.response}")
                    }
                    is APIResult.Failure -> {
                        errorAction.sendAction(result.error?.message ?: "Login Failed")
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
