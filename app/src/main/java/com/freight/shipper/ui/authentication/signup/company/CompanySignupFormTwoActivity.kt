package com.freight.shipper.ui.authentication.signup.company

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.freight.shipper.FreightApplication
import com.freight.shipper.R
import com.freight.shipper.core.platform.BaseActivity
import com.freight.shipper.core.platform.BaseViewModelFactory
import com.freight.shipper.databinding.ActivityCompanySignupSecondBinding
import com.freight.shipper.extensions.navigateToDashboard
import com.freight.shipper.extensions.setupToolbar
import com.freight.shipper.repository.AuthenticationRepository
import com.freight.shipper.ui.authentication.signup.CompanySignup
import kotlinx.android.synthetic.main.toolbar.*

class CompanySignupFormTwoActivity : BaseActivity() {

    // region - Private properties
    private val viewModel: CompanySignupViewModel by lazy {
        ViewModelProviders.of(this,
            BaseViewModelFactory {
                CompanySignupViewModel(
                    AuthenticationRepository(
                        FreightApplication.instance.meuralAPI,
                        FreightApplication.instance.loginManager
                    )
                )
            })
            .get(CompanySignupViewModel::class.java)
    }
    private lateinit var binding: ActivityCompanySignupSecondBinding
    // endregion

    // region - Overridden function
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_company_signup_second)
        binding.viewModel = viewModel
        viewModel.setCompanySignupForm(intent.getParcelableExtra<CompanySignup>("signup"))
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

    override fun onBackPressed() {
        viewModel?.onBackPressOnPageTwo()
        super.onBackPressed()
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
        viewModel.companySignupAction.observe(this, Observer {
            Toast.makeText(this@CompanySignupFormTwoActivity, it.first, Toast.LENGTH_LONG).show()
            if (it.second) navigateToDashboard()
        })
    }
    // endregion
}