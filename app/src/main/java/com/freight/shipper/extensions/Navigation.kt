package com.freight.shipper.extensions

import android.app.Activity
import android.content.Intent
import com.freight.shipper.ui.authentication.login.LoginActivity
import com.freight.shipper.ui.authentication.resetpassword.ResetPasswordActivity
import com.freight.shipper.ui.authentication.signup.SignupLandingActivity
import com.freight.shipper.ui.authentication.signup.company.CompanySignupActivity
import com.freight.shipper.ui.authentication.signup.individual.IndividualSignupActivity


/**
 * @CreatedBy Sanjay Kushwah on 8/2/2019.
 * sanjaykushwah0601@gmail.com
 */

fun Activity.startLoginActivity() {
    startActivity(Intent(this, LoginActivity::class.java))
}

fun Activity.navigateToSignupScreen() {
    startActivity(Intent(this, SignupLandingActivity::class.java))
}

fun Activity.navigateToResetPassword() {
    startActivity(Intent(this, ResetPasswordActivity::class.java))
}

fun Activity.navigateToIndividualSignup() {
    startActivity(Intent(this, IndividualSignupActivity::class.java))
}

fun Activity.navigateToCompanySignup() {
    startActivity(Intent(this, CompanySignupActivity::class.java))
}