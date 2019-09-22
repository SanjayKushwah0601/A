package com.freight.shipper.ui.profile.paymentdetails

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.freight.shipper.FreightApplication
import com.freight.shipper.R
import com.freight.shipper.core.platform.BaseActivity
import com.freight.shipper.core.platform.BaseViewModelFactory
import com.freight.shipper.databinding.ActivityPaymentDetailsBinding
import com.freight.shipper.extensions.navigateToDashboard
import com.freight.shipper.extensions.setupToolbar
import com.freight.shipper.extensions.showConfirmationMessage
import com.freight.shipper.extensions.showErrorMessage
import com.freight.shipper.repository.ProfileRepository
import kotlinx.android.synthetic.main.toolbar.*
import timber.log.Timber

class PaymentDetailsActivity : BaseActivity() {

    // region - Private fields
    private val viewModel: PaymentDetailsViewModel by lazy {
        ViewModelProviders.of(this, BaseViewModelFactory {
            PaymentDetailsViewModel(
                ProfileRepository(
                    FreightApplication.instance.api,
                    FreightApplication.instance.loginManager
                )
            )
        }).get(PaymentDetailsViewModel::class.java)
    }
    private lateinit var binding: ActivityPaymentDetailsBinding
    // endregion

    // region - Overridden functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment_details)
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
            subTitle = getString(R.string.payment_detail)
        )
//        tvToolbarTitle?.text = getString(R.string.company)
//        tvToolbarSubTitle?.text = getString(R.string.payment_detail)
    }

    private fun setupObservers() {
        viewModel.paymentDetailResponse.observe(this, Observer {
            // navigateToSignupScreen()
            viewModel.isLoading.set(false)
            Timber.d("Payment Submit Success")
            showConfirmationMessage(getString(R.string.payment_detail_added_success_msg))
            if (isSignUp) navigateToDashboard()
            finish()
        })
        viewModel.error.observe(this, Observer {
            viewModel.isLoading.set(false)
            showErrorMessage(it)
        })
    }
    // endregion

}
