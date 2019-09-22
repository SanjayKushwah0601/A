package com.freight.shipper.extensions

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.freight.shipper.core.persistence.network.response.NewLoad
import com.freight.shipper.model.IntentExtras
import com.freight.shipper.ui.addshipper.AddShipperActivity
import com.freight.shipper.ui.addvehicle.AddVehicleActivity
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
import com.freight.shipper.ui.paymentdetails.PaymentDetailsActivity
import com.freight.shipper.ui.vehiclelist.VehicleListActivity


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

fun Activity.navigateToPaymentDetails(isSignUp: Boolean = false) {
    val intent = Intent(this, PaymentDetailsActivity::class.java)
    intent.putExtra(IntentExtras.EXTRA_IS_SIGNUP, isSignUp)
    startActivity(intent)
}

fun Fragment.navigateToPaymentDetails(isSignUp: Boolean = false) {
    activity?.navigateToPaymentDetails(isSignUp)
}

fun Activity.navigateToAddVehicle(isSignUp: Boolean = false) {
    val intent = Intent(this, AddVehicleActivity::class.java)
    intent.putExtra(IntentExtras.EXTRA_IS_SIGNUP, isSignUp)
    startActivity(intent)
}

fun Fragment.navigateToAddVehicle(isSignUp: Boolean = false) {
    activity?.navigateToAddVehicle(isSignUp)
}

fun Activity.navigateToAddShipper(isSignUp: Boolean = false) {
    val intent = Intent(this, AddShipperActivity::class.java)
    intent.putExtra(IntentExtras.EXTRA_IS_SIGNUP, isSignUp)
    startActivity(intent)
}

fun Fragment.navigateToAddShipper(isSignUp: Boolean = false) {
    activity?.navigateToAddShipper(isSignUp)
}

fun Activity.navigateToVehicleList() {
    val intent = Intent(this, VehicleListActivity::class.java)
    startActivity(intent)
}

fun Fragment.navigateToVehicleList() {
    activity?.navigateToVehicleList()
}

fun AppCompatActivity.showCounterDialog(newLoad: NewLoad, listener: CounterDialog.CounterListener) {
    val counterDialog = CounterDialog.newInstance(newLoad)
    counterDialog.listener = listener
    counterDialog.show(supportFragmentManager, "fragment_edit_name")
}

fun Fragment.showCounterDialog(newLoad: NewLoad, listener: CounterDialog.CounterListener) {
    (activity as AppCompatActivity?)?.showCounterDialog(newLoad, listener)
}