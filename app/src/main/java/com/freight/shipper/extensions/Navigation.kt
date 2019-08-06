package com.freight.shipper.extensions

import android.app.Activity
import android.content.Intent
import com.freight.shipper.ui.authentication.login.LoginActivity
import com.freight.shipper.ui.authentication.resetpassword.ResetPasswordActivity
import com.freight.shipper.ui.authentication.signup.CompanySignup
import com.freight.shipper.ui.authentication.signup.SignupLandingActivity
import com.freight.shipper.ui.authentication.signup.company.CompanySignupActivity
import com.freight.shipper.ui.authentication.signup.company.CompanySignupFormTwoActivity
import com.freight.shipper.ui.authentication.signup.individual.IndividualSignupActivity
import com.freight.shipper.ui.dashboard.DashboardActivity


/**
 * @CreatedBy Sanjay Kushwah on 8/2/2019.
 * sanjaykushwah0601@gmail.com
 */

fun Activity.startLoginActivity() {
    startActivity(Intent(this, LoginActivity::class.java))
}

fun Activity.navigateToDashboard() {
    val intent = Intent(this, DashboardActivity::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    }
    startActivity(intent)
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

fun Activity.navigateToCompanySignupSecondPage(signupData: CompanySignup) {
    startActivity(Intent(this, CompanySignupFormTwoActivity::class.java).apply {
        putExtra("signup", signupData)
    })
}