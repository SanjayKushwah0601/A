package com.freight.shipper.ui.authentication.signup.company

import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import com.freight.shipper.FreightApplication
import com.freight.shipper.R
import com.freight.shipper.core.platform.BaseActivity
import com.freight.shipper.core.platform.BaseViewModelFactory
import com.freight.shipper.extensions.setupToolbar
import kotlinx.android.synthetic.main.toolbar.*

class CompanySignupFormTwoActivity : BaseActivity() {

    // region - Private properties
    private lateinit var viewModel: CompanySignupViewModel
    // endregion

    // region - Overridden function
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_signup_second)
        val model = CompanySignupModel(FreightApplication.instance.meuralAPI)
        viewModel = ViewModelProviders.of(this,
            BaseViewModelFactory { CompanySignupViewModel(model) })
            .get(CompanySignupViewModel::class.java)
        initUI()
        setupOnClicks()
        setupObservers()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
    // endregion

    // region - Private functions
    private fun initUI() {
        setupToolbar(toolbar, enableUpButton = true)
        tvToolbarTitle?.text = getString(R.string.company)
    }

    private fun setupOnClicks() {
//        buttonLogin?.setOnClickListener { viewModel.login() }
//        signUp?.setOnClickListener { viewModel.onSignupButtonClicked() }
//        forgotPassword?.setOnClickListener { viewModel.onResetButtonClicked() }
    }

    private fun setupObservers() {
//        viewModel.signupAction.observe(this, Observer {
//            navigateToSignupScreen()
//        })
//        viewModel.resetPasswordAction.observe(this, Observer {
//            navigateToResetPassword()
//        })
    }
    // endregion
}