package com.freight.shipper.ui.viewinvoice

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.freight.shipper.FreightApplication
import com.freight.shipper.R
import com.freight.shipper.core.persistence.network.response.PastLoad
import com.freight.shipper.core.platform.BaseActivity
import com.freight.shipper.core.platform.BaseViewModelFactory
import com.freight.shipper.databinding.ActivityViewInvoiceBinding
import com.freight.shipper.extensions.setupToolbar
import com.freight.shipper.extensions.showErrorMessage
import com.freight.shipper.model.IntentExtras
import com.freight.shipper.repository.LoadRepository
import kotlinx.android.synthetic.main.toolbar.*

class ViewInvoiceActivity : BaseActivity() {

    // region - Private properties
    private lateinit var binding: ActivityViewInvoiceBinding
    private val viewModel: ViewInvoiceViewModel by lazy {
        ViewModelProviders.of(this, BaseViewModelFactory {
            ViewInvoiceViewModel(
                intent.getParcelableExtra(IntentExtras.LOAD) as PastLoad,
                LoadRepository(
                    FreightApplication.instance.api, FreightApplication.instance.loginManager
                )
            )
        }).get(ViewInvoiceViewModel::class.java)
    }
    // endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
        setupObservers()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    // region - Private functions
    private fun setupObservers() {
        viewModel.loadDetail.observe(this, Observer { invoiceResponse ->
            binding.viewModel = viewModel
            binding.loadDetail = invoiceResponse
            binding.executePendingBindings()
        })

        viewModel.error.observe(this, Observer { errorMsg ->
            showErrorMessage(errorMsg)
        })
    }

    private fun initUI() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_invoice)
        binding.viewModel = viewModel
        binding.executePendingBindings()
        setupToolbar(toolbar, enableUpButton = true)
        tvToolbarTitle?.text = getString(R.string.invoice)
    }
    // endregion
}
