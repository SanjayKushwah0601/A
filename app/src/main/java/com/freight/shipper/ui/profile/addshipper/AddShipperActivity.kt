package com.freight.shipper.ui.profile.addshipper

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.freight.shipper.FreightApplication
import com.freight.shipper.R
import com.freight.shipper.core.persistence.network.response.Country
import com.freight.shipper.core.persistence.network.response.State
import com.freight.shipper.core.persistence.network.response.Vehicle
import com.freight.shipper.core.platform.BaseActivity
import com.freight.shipper.core.platform.BaseViewModelFactory
import com.freight.shipper.core.platform.HintSpinnerAdapter
import com.freight.shipper.databinding.ActivityAddShipperBinding
import com.freight.shipper.extensions.*
import com.freight.shipper.repository.ProfileRepository
import kotlinx.android.synthetic.main.activity_add_shipper.*
import kotlinx.android.synthetic.main.toolbar.*


class AddShipperActivity : BaseActivity() {

    // region - Private fields
    private val viewModel: AddShipperViewModel by lazy {
        ViewModelProviders.of(this, BaseViewModelFactory {
            AddShipperViewModel(
                ProfileRepository(
                    FreightApplication.instance.api,
                    FreightApplication.instance.loginManager
                )
            )
        }).get(AddShipperViewModel::class.java)
    }
    private lateinit var binding: ActivityAddShipperBinding

    private val countryAdapter by lazy {
        object : HintSpinnerAdapter<Country>(
            this@AddShipperActivity, mutableListOf(),
            getString(R.string.select_country)
        ) {
            override fun getLabelFor(item: Country): String {
                return item.countryName
            }
        }
    }
    private val stateAdapter by lazy {
        object : HintSpinnerAdapter<State>(
            this@AddShipperActivity, mutableListOf(),
            getString(R.string.select_state)
        ) {
            override fun getLabelFor(item: State): String {
                return item.stateName
            }
        }
    }

    private val vehicleAdapter by lazy {
        object : HintSpinnerAdapter<Vehicle>(
            this@AddShipperActivity, mutableListOf(),
            getString(R.string.select_vehicle)
        ) {
            override fun getLabelFor(item: Vehicle): String {
                return item.vehicleName
            }
        }
    }
    // endregion

    // region - Overridden functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_shipper)
        binding.viewModel = viewModel
        initUI()
        setupObservers()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
    // endregion

    // region - Private functions
    private fun initUI() {
        setupToolbar(
            toolbar, enableUpButton = !isSignUp,
            title = getString(R.string.company),
            subTitle = getString(R.string.add_shipper)
        )
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
                viewModel.requestModel.state = it.stateName
            }
        }
        viewModel.vehicleList.observeForever { list ->
            spVehicle.adapter = vehicleAdapter
            vehicleAdapter.setList(list)
            spVehicle.setOnItemSelectListener(vehicleAdapter) {
                Log.d("SelectedItem", "${it?.vehicleName}")
                viewModel.requestModel.vehicleId = it.vehicleId
            }
        }
        viewModel.addShipperResponse.observe(this, Observer {
            viewModel.isLoading.set(false)
            showConfirmationMessage(getString(R.string.shipper_added_success_message))
            if (isSignUp && !isIndividual) navigateToPaymentDetails(isSignUp)
            // TODO : Set result to list
            finish()
            // navigateToSignupScreen()
        })
        viewModel.error.observe(this, Observer {
            viewModel.isLoading.set(false)
            showErrorMessage(it)
        })
    }
    // endregion
}
