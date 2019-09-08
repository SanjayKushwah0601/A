package com.freight.shipper.ui.profile.addvehicle

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.freight.shipper.FreightApplication
import com.freight.shipper.R
import com.freight.shipper.core.platform.BaseActivity
import com.freight.shipper.core.platform.BaseViewModelFactory
import com.freight.shipper.core.platform.HintSpinnerAdapter
import com.freight.shipper.databinding.ActivityAddVehicleBinding
import com.freight.shipper.extensions.setOnItemSelectListener
import com.freight.shipper.extensions.setupToolbar
import com.freight.shipper.model.VehicleType
import com.freight.shipper.repository.ProfileRepository
import kotlinx.android.synthetic.main.activity_add_vehicle.*
import kotlinx.android.synthetic.main.toolbar.*
import timber.log.Timber

class AddVehicleActivity : BaseActivity() {

    // region - Private fields
    private val viewModel: AddVehicleViewModel by lazy {
        ViewModelProviders.of(this, BaseViewModelFactory {
            AddVehicleViewModel(
                ProfileRepository(
                    FreightApplication.instance.api,
                    FreightApplication.instance.loginManager
                )
            )
        }).get(AddVehicleViewModel::class.java)
    }
    private lateinit var binding: ActivityAddVehicleBinding

    private val vehicleTypeAdapter by lazy {
        object : HintSpinnerAdapter<VehicleType>(
            this@AddVehicleActivity, mutableListOf(),
            getString(R.string.select_vehicle_type)
        ) {
            override fun getLabelFor(item: VehicleType): String {
                return item.vehicleType
            }
        }
    }
    // endregion

    // region - Overridden functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_vehicle)
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
            toolbar, enableUpButton = true,
            title = getString(R.string.company),
            subTitle = getString(R.string.add_vehicle)
        )
//        tvToolbarTitle?.text = getString(R.string.company)
//        tvToolbarSubTitle?.text = getString(R.string.payment_detail)
    }

    private fun setupObservers() {
        viewModel.vehicleType.observeForever { list ->
            spVehicleType.adapter = vehicleTypeAdapter
            vehicleTypeAdapter.setList(list)
            spVehicleType.setOnItemSelectListener(vehicleTypeAdapter) {
                Log.d("SelectedItem", "${it?.vehicleType}")
//                viewModel.requestModel.loadType = it.vehicleType
            }
        }
        viewModel.paymentDetailResponse.observe(this, Observer {
            // navigateToSignupScreen()
            viewModel.isLoading.set(false)
            Timber.d("Payment Submit Success")
            Toast.makeText(
                this@AddVehicleActivity,
                getString(R.string.payment_detail_added_success_msg),
                Toast.LENGTH_LONG
            ).show()
        })
        viewModel.error.observe(this, Observer {
            viewModel.isLoading.set(false)
            Toast.makeText(this@AddVehicleActivity, it, Toast.LENGTH_LONG).show()
        })
    }
    // endregion

}
