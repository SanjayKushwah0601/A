package com.freight.shipper.ui.invoice

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.freight.shipper.FreightApplication
import com.freight.shipper.R
import com.freight.shipper.core.persistence.network.response.ActiveLoad
import com.freight.shipper.core.platform.BaseActivity
import com.freight.shipper.core.platform.BaseViewModelFactory
import com.freight.shipper.databinding.ActivityInvoiceBinding
import com.freight.shipper.extensions.navigateToDashboard
import com.freight.shipper.extensions.showErrorMessage
import com.freight.shipper.extensions.showSingleOptionAlertDialog
import com.freight.shipper.model.IntentExtras
import com.freight.shipper.repository.RouteRepository
import com.freight.shipper.ui.bookings.assigned.LoadPagerFragment
import com.freight.shipper.ui.dashboard.DashboardActivity
import com.freight.shipper.utils.customviews.SignatureView
import kotlinx.android.synthetic.main.activity_invoice.*

class InvoiceActivity : BaseActivity() {

    // region - Private properties
    private lateinit var binding: ActivityInvoiceBinding
    private lateinit var mSignature: SignatureView
    private val viewModel: InvoiceViewModel by lazy {
        ViewModelProviders.of(this, BaseViewModelFactory {
            InvoiceViewModel(
                intent.getParcelableExtra(IntentExtras.ACTIVE_LOAD) as ActiveLoad,
                RouteRepository(
                    FreightApplication.instance.api, FreightApplication.instance.loginManager
                )
            )
        }).get(InvoiceViewModel::class.java)
    }
    // endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
        setupObservers()
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

        viewModel.submitInvoice.observe(this, Observer {
            val signatureFile = mSignature.print(it.first)
            if (signatureFile != null) {
                viewModel.uploadSignature(it.second, signatureFile)
            } else {
                showErrorMessage(getString(R.string.cannot_take_sign))
            }
        })

        viewModel.submitInvoiceResponse.observe(this, Observer {
            showSingleOptionAlertDialog(
                R.string.alert_title, R.string.invoice_success_message, cancelable = false
            ) {
                navigateToDashboard(
                    DashboardActivity.START_SCREEN_ASSIGNED, LoadPagerFragment.PAST_LOAD
                )
            }
        })
    }

    private fun initUI() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_invoice)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        mSignature = SignatureView(this@InvoiceActivity, null, scrollView)
        mSignature.setBackgroundColor(Color.TRANSPARENT)
        // Dynamically generating Layout through java code
        signatureLayout.addView(
            mSignature, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
    }
    // endregion
}
