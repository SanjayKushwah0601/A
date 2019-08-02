package com.freight.shipper.ui.authentication.signup

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.freight.shipper.R
import com.freight.shipper.core.platform.BaseActivity
import com.freight.shipper.core.platform.BaseViewModelFactory

class SignupActivity : BaseActivity() {

    // region - Private fields
    private lateinit var viewModel: SignupViewModel
    // endregion

    // region - Overridden functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        viewModel = ViewModelProviders.of(this,
            BaseViewModelFactory { SignupViewModel() })
            .get(SignupViewModel::class.java)
    }
    // endregion

}
