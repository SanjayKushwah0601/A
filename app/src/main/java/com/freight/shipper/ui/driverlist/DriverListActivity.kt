package com.freight.shipper.ui.driverlist

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
import com.freight.shipper.databinding.ActivityDriverListBinding
import com.freight.shipper.extensions.navigateToAddShipper
import com.freight.shipper.extensions.setupToolbar
import com.freight.shipper.extensions.showErrorMessage
import com.freight.shipper.repository.ProfileRepository
import com.freight.shipper.ui.driverlist.recyclerview.DriverAdapter
import kotlinx.android.synthetic.main.activity_vehicle_list.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * @CreatedBy Sanjay Kushwah on 9/22/2019.
 * sanjaykushwah0601@gmail.com
 */
class DriverListActivity : BaseActivity() {

    // region - Private properties
    private val viewModel: DriverViewModel by lazy {
        ViewModelProviders.of(this,
            BaseViewModelFactory {
                DriverViewModel(
                    ProfileRepository(
                        FreightApplication.instance.api,
                        FreightApplication.instance.loginManager
                    )
                )
            }).get(DriverViewModel::class.java)
    }
    private lateinit var binding: ActivityDriverListBinding
    private lateinit var adapter: DriverAdapter
    // endregion

    // region - Lifecycle functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_driver_list)
        binding.viewModel = viewModel
        initUI()
        setAdapter()
        setObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshDriverList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (!isIndividual) menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == R.id.menu_add) {
            navigateToAddShipper()
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
            toolbar, enableUpButton = true, title = getString(R.string.shipper_list)
        )
    }

    private fun setAdapter() {
        adapter = DriverAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this@DriverListActivity)
        recyclerView.adapter = adapter
    }

    private fun setObservers() {
        viewModel.driverList.observe(this, Observer {
            adapter.addListItems(it)
        })

        viewModel.driverListError.observe(this, Observer {
            showErrorMessage(it)
        })
    }
    // endregion

}
