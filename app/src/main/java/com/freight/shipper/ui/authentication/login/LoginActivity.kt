package com.freight.shipper.ui.authentication.login

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.freight.shipper.FreightApplication
import com.freight.shipper.R
import com.freight.shipper.core.platform.BaseActivity
import com.freight.shipper.core.platform.BaseViewModelFactory
import com.freight.shipper.extensions.navigateToResetPassword
import com.freight.shipper.extensions.navigateToSignupScreen
import com.freight.shipper.extensions.setupToolbar
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.toolbar.*

class LoginActivity : BaseActivity() {

    // region - Private fields
    private lateinit var viewModel: LoginViewModel
    // endregion

    // region - Overridden functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val loginModel = LoginModel(FreightApplication.instance.meuralAPI)
        viewModel = ViewModelProviders.of(this,
            BaseViewModelFactory { LoginViewModel(loginModel) })
            .get(LoginViewModel::class.java)
        initUI()
        setupOnClicks()
        setupObservers()
    }

    private fun initUI() {
        setupToolbar(toolbar, enableUpButton = false)
        tvToolbarTitle?.text = getString(R.string.login)
    }
    // endregion

    // region - Private functions
    private fun setupOnClicks() {
        buttonLogin?.setOnClickListener { viewModel.login() }
        signUp?.setOnClickListener { viewModel.onSignupButtonClicked() }
        forgotPassword?.setOnClickListener { viewModel.onResetButtonClicked() }
    }

    private fun setupObservers() {
        viewModel.signupAction.observe(this, Observer {
            navigateToSignupScreen()
        })
        viewModel.resetPasswordAction.observe(this, Observer {
            navigateToResetPassword()
        })
    }
    // endregion

}
