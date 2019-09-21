package com.freight.shipper.ui.authentication.signup.company

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.freight.shipper.FreightApplication
import com.freight.shipper.R
import com.freight.shipper.core.platform.BaseActivity
import com.freight.shipper.core.platform.BaseViewModelFactory
import com.freight.shipper.core.platform.HintSpinnerAdapter
import com.freight.shipper.databinding.ActivityCompanySignupFirstBinding
import com.freight.shipper.extensions.navigateToCompanySignupSecondPage
import com.freight.shipper.extensions.setOnItemSelectListener
import com.freight.shipper.extensions.setupToolbar
import com.freight.shipper.model.Country
import com.freight.shipper.model.State
import com.freight.shipper.repository.AuthenticationRepository
import kotlinx.android.synthetic.main.activity_company_signup_first.*
import kotlinx.android.synthetic.main.toolbar.*

class CompanySignupActivity : BaseActivity() {

    // region - Private properties
    private val viewModel: CompanySignupViewModel by lazy {
        ViewModelProviders.of(this,
            BaseViewModelFactory {
                CompanySignupViewModel(
                    AuthenticationRepository(
                        FreightApplication.instance.api,
                        FreightApplication.instance.loginManager
                    )
                )
            })
            .get(CompanySignupViewModel::class.java)
    }

    private val countryAdapter by lazy {
        object : HintSpinnerAdapter<Country>(
            this@CompanySignupActivity, mutableListOf(),
            getString(R.string.select_country)
        ) {
            override fun getLabelFor(item: Country): String {
                return item.countryName
            }
        }
    }
    private val stateAdapter by lazy {
        object : HintSpinnerAdapter<State>(
            this@CompanySignupActivity, mutableListOf(),
            getString(R.string.select_state)
        ) {
            override fun getLabelFor(item: State): String {
                return item.stateName
            }
        }
    }

    private lateinit var binding: ActivityCompanySignupFirstBinding
    // endregion

    // region - Overridden function
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_company_signup_first)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_company_signup_first)
        binding.viewModel = viewModel
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
        buttonNext?.setOnClickListener { viewModel.onNextButtonClicked() }
    }

    private fun setupObservers() {
        viewModel.countries.observeForever { list ->
            spCountry.adapter = countryAdapter
            countryAdapter.setList(list)
            spCountry.setOnItemSelectListener(countryAdapter) {
                Log.d("SelectedItem", "${it?.countryName}")
                viewModel.onCountrySelect(it)
            }
        }
        viewModel.states.observeForever { list ->
            spState.adapter = stateAdapter
            stateAdapter.setList(list)
            spState.setOnItemSelectListener(stateAdapter) {
                Log.d("SelectedItem", "${it?.stateName}")
                viewModel.companySignup.state = it.stateName
            }
        }
        viewModel.nextButtonAction.observe(this, Observer {
            navigateToCompanySignupSecondPage(it)
        })
    }
    // endregion
}