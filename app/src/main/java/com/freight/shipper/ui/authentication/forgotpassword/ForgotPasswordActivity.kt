package com.freight.shipper.ui.authentication.forgotpassword

import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.freight.shipper.FreightApplication
import com.freight.shipper.R
import com.freight.shipper.core.platform.BaseActivity
import com.freight.shipper.core.platform.BaseViewModelFactory
import com.freight.shipper.databinding.ActivityForgotPasswordBinding
import com.freight.shipper.extensions.navigateToResetPassword
import com.freight.shipper.extensions.setKeyboardShown
import com.freight.shipper.extensions.setupToolbar
import com.freight.shipper.extensions.showErrorMessage
import com.freight.shipper.repository.AuthenticationRepository
import kotlinx.android.synthetic.main.toolbar.*
import timber.log.Timber

class ForgotPasswordActivity : BaseActivity() {

    // region - Private fields
    private val viewModel: ForgotPasswordViewModel by lazy {
        ViewModelProviders.of(this,
            BaseViewModelFactory {
                ForgotPasswordViewModel(
                    AuthenticationRepository(
                        FreightApplication.instance.meuralAPI,
                        FreightApplication.instance.loginManager
                    )
                )
            })
            .get(ForgotPasswordViewModel::class.java)
    }
    private lateinit var binding: ActivityForgotPasswordBinding
    // endregion

    // region - Overridden functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password)
        binding.viewModel = viewModel
        initUi()
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
    private fun initUi() {
        setupToolbar(toolbar, enableUpButton = true)
        tvToolbarTitle?.text = getString(R.string.forgot_password)
    }

    private fun setupObservers() {
        viewModel.isLoading.observe(this, Observer {
            Timber.d("Loading...")
            binding.root?.let { it.setKeyboardShown(it.context, false) }
        })

        viewModel.error.observe(this, Observer {
            Timber.d("ApiError... $it")
            showErrorMessage(it)
        })

        viewModel.forgotPasswordResponse.observe(this, Observer {
            Timber.d("Response... ${it.toString()}")
            navigateToResetPassword()
        })
    }
    // endregion

}
