package com.freight.shipper.core.platform

import androidx.appcompat.app.AppCompatActivity
import com.freight.shipper.FreightApplication
import com.freight.shipper.model.IntentExtras
import com.freight.shipper.model.UserRole

/**
 * @author Sanjay Kushwah
 * @Created on 12/6/19.
 */
abstract class BaseActivity : AppCompatActivity() {

    val isSignUp by lazy { intent.getBooleanExtra(IntentExtras.EXTRA_IS_SIGNUP, false) }
    val isIndividual by lazy { FreightApplication.instance.loginManager.userRole == UserRole.SHIPPER }
}