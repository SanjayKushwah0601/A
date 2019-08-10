package com.freight.shipper.ui.authentication.login

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.freight.shipper.FreightApplication
import com.freight.shipper.R
import com.freight.shipper.core.platform.BaseActivity
import com.freight.shipper.core.platform.BaseViewModelFactory
import com.freight.shipper.databinding.ActivityLoginBinding
import com.freight.shipper.extensions.*
import com.freight.shipper.repository.AuthenticationRepository
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.toolbar.*

class LoginActivity : BaseActivity() {

    // region - Private fields
    private val viewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this, BaseViewModelFactory {
            LoginViewModel(
                AuthenticationRepository(
                    FreightApplication.instance.meuralAPI,
                    FreightApplication.instance.loginManager
                )
            )
        }).get(LoginViewModel::class.java)
    }
    private lateinit var binding: ActivityLoginBinding
    // endregion

    // region - Overridden functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = viewModel
        initUI()
        setupOnClicks()
        setupObservers()
    }
    // endregion

    // region - Private functions
    private fun initUI() {
        setupToolbar(toolbar, enableUpButton = false)
        tvToolbarTitle?.text = getString(R.string.login)
    }

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
            navigateToForgotPassword()
        })
        viewModel.loginStatusAction.observe(this, Observer {
            navigateToDashboard()
        })
        viewModel.errorAction.observe(this, Observer {
            Toast.makeText(this@LoginActivity, it, Toast.LENGTH_LONG).show()
        })
    }
    // endregion

}
