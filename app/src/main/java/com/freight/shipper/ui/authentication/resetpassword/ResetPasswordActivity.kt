package com.freight.shipper.ui.authentication.resetpassword

import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import com.freight.shipper.R
import com.freight.shipper.core.platform.BaseActivity
import com.freight.shipper.core.platform.BaseViewModelFactory
import com.freight.shipper.extensions.setupToolbar
import kotlinx.android.synthetic.main.toolbar.*

class ResetPasswordActivity : BaseActivity() {

    // region - Private fields
    private lateinit var viewModel: ResetPasswordViewModel
    // endregion

    // region - Overridden functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        viewModel = ViewModelProviders.of(this,
            BaseViewModelFactory { ResetPasswordViewModel() })
            .get(ResetPasswordViewModel::class.java)
        initUi()
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
        tvToolbarTitle?.text = getString(R.string.reset_password)
    }
    // endregion

}
