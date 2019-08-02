package com.freight.shipper.ui.authentication.resetpassword

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.freight.shipper.R
import com.freight.shipper.core.platform.BaseActivity
import com.freight.shipper.core.platform.BaseViewModelFactory

class ResetPasswordActivity : BaseActivity() {

    // region - Private fields
    private lateinit var viewModel: ResetPasswordViewModel
    // endregion

    // region - Overridden functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        viewModel = ViewModelProviders.of(this,
            BaseViewModelFactory { ResetPasswordViewModel() })
            .get(ResetPasswordViewModel::class.java)
    }
    // endregion

}
