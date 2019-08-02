package com.freight.shipper.ui.authentication.login

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.freight.shipper.R
import com.freight.shipper.core.platform.BaseActivity
import com.freight.shipper.core.platform.BaseViewModelFactory

class LoginActivity : BaseActivity() {

    // region - Private fields
    private lateinit var viewModel: LoginViewModel
    // endregion

    // region - Overridden functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewModel = ViewModelProviders.of(this,
            BaseViewModelFactory { LoginViewModel() })
            .get(LoginViewModel::class.java)
    }
    // endregion

}
