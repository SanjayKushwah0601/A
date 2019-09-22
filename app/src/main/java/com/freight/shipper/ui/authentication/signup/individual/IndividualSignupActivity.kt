package com.freight.shipper.ui.authentication.signup.individual

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.freight.shipper.FreightApplication
import com.freight.shipper.R
import com.freight.shipper.core.persistence.network.response.Country
import com.freight.shipper.core.persistence.network.response.State
import com.freight.shipper.core.platform.BaseActivity
import com.freight.shipper.core.platform.BaseViewModelFactory
import com.freight.shipper.core.platform.HintSpinnerAdapter
import com.freight.shipper.databinding.ActivityIndividualSignupBinding
import com.freight.shipper.extensions.navigateToAddVehicle
import com.freight.shipper.extensions.setOnItemSelectListener
import com.freight.shipper.extensions.setupToolbar
import com.freight.shipper.extensions.showConfirmationMessage
import com.freight.shipper.repository.AuthenticationRepository
import kotlinx.android.synthetic.main.activity_company_signup_first.*
import kotlinx.android.synthetic.main.toolbar.*

class IndividualSignupActivity : BaseActivity() {

    private lateinit var binding: ActivityIndividualSignupBinding
    private val viewModel: SignupViewModel by lazy {
        ViewModelProviders.of(this,
            BaseViewModelFactory {
                SignupViewModel(
                    AuthenticationRepository(
                        FreightApplication.instance.api,
                        FreightApplication.instance.loginManager
                    )
                )
            })
            .get(SignupViewModel::class.java)
    }

    private val countryAdapter by lazy {
        object : HintSpinnerAdapter<Country>(
            this@IndividualSignupActivity, mutableListOf(),
            getString(R.string.select_country)
        ) {
            override fun getLabelFor(item: Country): String {
                return item.countryName
            }
        }
    }
    private val stateAdapter by lazy {
        object : HintSpinnerAdapter<State>(
            this@IndividualSignupActivity, mutableListOf(),
            getString(R.string.select_state)
        ) {
            override fun getLabelFor(item: State): String {
                return item.stateName
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_individual_signup)
        binding.viewModel = viewModel
        initUI()
        setupOnObservers()
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
        tvToolbarTitle?.text = getString(R.string.signup)
    }

    private fun setupOnObservers() {
        viewModel.countries.observe(this, Observer { list ->
            spCountry.adapter = countryAdapter
            countryAdapter.setList(list)
            spCountry.setOnItemSelectListener(countryAdapter) {
                Log.d("SelectedItem", "${it?.countryName}")
                viewModel.onCountrySelect(it)
            }
        })
        viewModel.states.observe(this, Observer { list ->
            spState.adapter = stateAdapter
            stateAdapter.setList(list)
            spState.setOnItemSelectListener(stateAdapter) {
                Log.d("SelectedItem", "${it?.stateName}")
                viewModel.companySignup.state = it.stateName
            }
        })
        viewModel.companySignupAction.observe(this, Observer {
            showConfirmationMessage(it.first)
            if (it.second) {
                navigateToAddVehicle(true)
                finish()
            }
        })
    }

}
