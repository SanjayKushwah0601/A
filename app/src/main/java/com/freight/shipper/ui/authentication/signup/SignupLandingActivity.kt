package com.freight.shipper.ui.authentication.signup

import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.freight.shipper.R
import com.freight.shipper.core.platform.BaseActivity
import com.freight.shipper.core.platform.BaseViewModelFactory
import com.freight.shipper.extensions.navigateToCompanySignup
import com.freight.shipper.extensions.navigateToIndividualSignup
import com.freight.shipper.extensions.setupToolbar
import kotlinx.android.synthetic.main.activity_signup_landing.*
import kotlinx.android.synthetic.main.toolbar.*

class SignupLandingActivity : BaseActivity() {

    // region - Private fields
    private lateinit var viewModel: SignupLandingViewModel
    // endregion

    // region - Overridden functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_landing)
        viewModel = ViewModelProviders.of(this,
            BaseViewModelFactory { SignupLandingViewModel() })
            .get(SignupLandingViewModel::class.java)

        initUi()
        setupClickEvents()
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
    private fun initUi() {
        setupToolbar(toolbar, enableUpButton = true)
        tvToolbarTitle?.text = getString(R.string.signup)
    }

    private fun setupClickEvents() {
        buttonCompany?.setOnClickListener { viewModel.onCompanyButtonClicked() }
        buttonIndividual?.setOnClickListener { viewModel.onIndividualButtonClicked() }
    }

    private fun setupObservers() {
        viewModel.companyAction.observe(this, Observer {
            navigateToCompanySignup()
        })
        viewModel.individualAction.observe(this, Observer {
            navigateToIndividualSignup()
        })
    }
    // endregion
}
