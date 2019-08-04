package com.freight.shipper.ui.authentication.signup.company

import android.os.Bundle
import android.os.PersistableBundle
import androidx.lifecycle.ViewModelProviders
import com.freight.shipper.R
import com.freight.shipper.core.platform.BaseActivity
import com.freight.shipper.ui.authentication.login.LoginViewModel

class CompanySignupActivity : BaseActivity() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.login_fragment)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }
}