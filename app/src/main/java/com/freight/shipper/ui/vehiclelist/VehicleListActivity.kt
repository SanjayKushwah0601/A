package com.freight.shipper.ui.vehiclelist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.freight.shipper.FreightApplication
import com.freight.shipper.R
import com.freight.shipper.core.platform.BaseActivity
import com.freight.shipper.core.platform.BaseViewModelFactory
import com.freight.shipper.databinding.ActivityVehicleListBinding
import com.freight.shipper.extensions.navigateToAddVehicle
import com.freight.shipper.extensions.setupToolbar
import com.freight.shipper.extensions.showErrorMessage
import com.freight.shipper.repository.ProfileRepository
import com.freight.shipper.ui.vehiclelist.recyclerview.VehicleAdapter
import kotlinx.android.synthetic.main.activity_vehicle_list.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * @CreatedBy Sanjay Kushwah on 9/22/2019.
 * sanjaykushwah0601@gmail.com
 */
class VehicleListActivity : BaseActivity() {

    // region - Private properties
    private val viewModel: VehicleViewModel by lazy {
        ViewModelProviders.of(this,
            BaseViewModelFactory {
                VehicleViewModel(
                    ProfileRepository(
                        FreightApplication.instance.api,
                        FreightApplication.instance.loginManager
                    )
                )
            }).get(VehicleViewModel::class.java)
    }
    private lateinit var binding: ActivityVehicleListBinding
    private lateinit var adapter: VehicleAdapter
    // endregion

    // region - Lifecycle functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_vehicle_list)
        binding.viewModel = viewModel
        initUI()
        setAdapter()
        setObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshVehicleList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (!isIndividual) menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == R.id.menu_add) {
            navigateToAddVehicle()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
    // endregion

    // region - Private functions
    private fun initUI() {
        setupToolbar(
            toolbar, enableUpButton = true, title = getString(R.string.vehicle_list)
        )
    }

    private fun setAdapter() {
        adapter = VehicleAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this@VehicleListActivity)
        recyclerView.adapter = adapter
    }

    private fun setObservers() {
        viewModel.vehicleList.observe(this, Observer {
            adapter.addListItems(it)
        })

        viewModel.vehicleListError.observe(this, Observer {
            showErrorMessage(it)
        })
    }
    // endregion

}
