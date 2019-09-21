package com.freight.shipper.extensions

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.freight.shipper.model.NewLoad
import com.freight.shipper.ui.authentication.forgotpassword.ForgotPasswordActivity
import com.freight.shipper.ui.authentication.login.LoginActivity
import com.freight.shipper.ui.authentication.resetpassword.ResetPasswordActivity
import com.freight.shipper.ui.authentication.signup.CompanySignup
import com.freight.shipper.ui.authentication.signup.SignupLandingActivity
import com.freight.shipper.ui.authentication.signup.company.CompanySignupActivity
import com.freight.shipper.ui.authentication.signup.company.CompanySignupFormTwoActivity
import com.freight.shipper.ui.authentication.signup.individual.IndividualSignupActivity
import com.freight.shipper.ui.bookings.counterdialog.CounterDialog
import com.freight.shipper.ui.dashboard.DashboardActivity
import com.freight.shipper.ui.profile.addshipper.AddShipperActivity
import com.freight.shipper.ui.profile.addvehicle.AddVehicleActivity
import com.freight.shipper.ui.profile.paymentdetails.PaymentDetailsActivity


/**
 * @CreatedBy Sanjay Kushwah on 8/2/2019.
 * sanjaykushwah0601@gmail.com
 */

fun Activity.startLoginActivity() {
    startActivity(Intent(this, LoginActivity::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    })
}

fun Fragment.startLoginActivity() {
    activity?.startLoginActivity()
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

fun Activity.navigateToForgotPassword() {
    startActivity(Intent(this, ForgotPasswordActivity::class.java))
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

fun Activity.navigateToPaymentDetails() {
    startActivity(Intent(this, PaymentDetailsActivity::class.java))
}

fun Fragment.navigateToPaymentDetails() {
    activity?.navigateToPaymentDetails()
}

fun Activity.navigateToAddVehicle() {
    startActivity(Intent(this, AddVehicleActivity::class.java))
}

fun Fragment.navigateToAddVehicle() {
    activity?.navigateToAddVehicle()
}

fun Activity.navigateToAddShipper() {
    startActivity(Intent(this, AddShipperActivity::class.java))
}

fun Fragment.navigateToAddShipper() {
    activity?.navigateToAddShipper()
}

fun AppCompatActivity.showCounterDialog(newLoad: NewLoad, listener: CounterDialog.CounterListener) {
    val counterDialog = CounterDialog.newInstance(newLoad)
    counterDialog.listener = listener
    counterDialog.show(supportFragmentManager, "fragment_edit_name")
}

fun Fragment.showCounterDialog(newLoad: NewLoad, listener: CounterDialog.CounterListener) {
    (activity as AppCompatActivity?)?.showCounterDialog(newLoad, listener)
}