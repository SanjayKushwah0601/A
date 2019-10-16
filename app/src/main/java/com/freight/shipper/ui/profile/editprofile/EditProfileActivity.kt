package com.freight.shipper.ui.profile.editprofile

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.freight.shipper.FreightApplication
import com.freight.shipper.R
import com.freight.shipper.core.platform.BaseActivity
import com.freight.shipper.core.platform.BaseViewModelFactory
import com.freight.shipper.databinding.ActivityEditProfileBinding
import com.freight.shipper.extensions.setupToolbar
import com.freight.shipper.repository.ProfileRepository
import com.freight.shipper.ui.profile.ProfileViewModel
import kotlinx.android.synthetic.main.toolbar.*

class EditProfileActivity : BaseActivity() {

    // region - Private fields
    private val viewModel: ProfileViewModel by lazy {
        ViewModelProviders.of(this, BaseViewModelFactory {
            ProfileViewModel(
                ProfileRepository(
                    FreightApplication.instance.api,
                    FreightApplication.instance.loginManager
                )
            )
        }).get(ProfileViewModel::class.java)
    }
    private lateinit var binding: ActivityEditProfileBinding
    // endregion

    // region - Overridden functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile)
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
            title = getString(R.string.edit_profile)
        )
//        tvToolbarTitle?.text = getString(R.string.company)
//        tvToolbarSubTitle?.text = getString(R.string.payment_detail)
    }

    private fun setupObservers() {
        /*viewModel.paymentDetailResponse.observe(this, Observer {
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
        })*/
    }
    // endregion

}
