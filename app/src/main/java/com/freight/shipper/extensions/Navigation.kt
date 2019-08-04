package com.freight.shipper.extensions

import android.app.Activity
import android.content.Intent
import com.freight.shipper.ui.authentication.login.LoginActivity
import com.freight.shipper.ui.authentication.resetpassword.ResetPasswordActivity
import com.freight.shipper.ui.authentication.signup.SignupActivity


/**
 * @CreatedBy Sanjay Kushwah on 8/2/2019.
 * sanjaykushwah0601@gmail.com
 */

fun Activity.startLoginActivity() {
    startActivity(Intent(this, LoginActivity::class.java))
}

fun Activity.navigateToSignupScreen() {
    startActivity(Intent(this, SignupActivity::class.java))
}

fun Activity.navigateToResetPassword() {
    startActivity(Intent(this, ResetPasswordActivity::class.java))
}