package com.freight.shipper.extensions

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.freight.shipper.core.persistence.network.response.ActiveLoad
import com.freight.shipper.core.persistence.network.response.NewLoad
import com.freight.shipper.core.persistence.network.response.User
import com.freight.shipper.model.IntentExtras
import com.freight.shipper.model.LoadFilter
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
import com.freight.shipper.ui.bookings.filter.BookingFilterBottomSheet
import com.freight.shipper.ui.dashboard.DashboardActivity
import com.freight.shipper.ui.driverlist.DriverListActivity
import com.freight.shipper.ui.invoice.InvoiceActivity
import com.freight.shipper.ui.paymentdetails.PaymentDetailsActivity
import com.freight.shipper.ui.profile.editprofile.EditProfileActivity
import com.freight.shipper.ui.route.destination.DestinationRouteActivity
import com.freight.shipper.ui.route.pickup.PickupRouteActivity
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

fun Activity.navigateToDashboard(
    startScreen: Int = DashboardActivity.START_SCREEN_HOME, pageToBeShow: Int? = null
) {
    val intent = Intent(this, DashboardActivity::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        putExtra(DashboardActivity.EXTRA_START_SCREEN, startScreen)
        putExtra(DashboardActivity.EXTRA_PAGE_NUMBER, pageToBeShow)
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

fun Activity.navigateToDriverList() {
    val intent = Intent(this, DriverListActivity::class.java)
    startActivity(intent)
}

fun Fragment.navigateToDriverList() {
    activity?.navigateToDriverList()
}

fun AppCompatActivity.showCounterDialog(newLoad: NewLoad, listener: CounterDialog.CounterListener) {
    val counterDialog = CounterDialog.newInstance(newLoad)
    counterDialog.listener = listener
    counterDialog.show(supportFragmentManager, "fragment_edit_name")
}

fun Fragment.showCounterDialog(newLoad: NewLoad, listener: CounterDialog.CounterListener) {
    (activity as AppCompatActivity?)?.showCounterDialog(newLoad, listener)
}

fun Activity.navigateToRouteActivity(activeLoad: ActiveLoad) {
    startActivity(Intent(this, PickupRouteActivity::class.java).apply {
        putExtra(IntentExtras.ACTIVE_LOAD, activeLoad)
    })
}

fun Fragment.navigateToRouteActivity(activeLoad: ActiveLoad) {
    activity?.navigateToRouteActivity(activeLoad)
}

fun Activity.navigateToEditProfile(user: User, requestCode: Int) {
    startActivityForResult(Intent(this, EditProfileActivity::class.java).apply {
        putExtra(IntentExtras.USER, user)
    }, requestCode)
}

fun Fragment.navigateToEditProfile(user: User, requestCode: Int) {
    val intent = Intent(activity, EditProfileActivity::class.java).apply {
        putExtra(IntentExtras.USER, user)
    }
    startActivityForResult(intent, requestCode)
}


fun Activity.navigateToDestinationRouteActivity(activeLoad: ActiveLoad) {
    startActivity(Intent(this, DestinationRouteActivity::class.java).apply {
        putExtra(IntentExtras.ACTIVE_LOAD, activeLoad)
    })
}

fun Fragment.navigateToDestinationRouteActivity(activeLoad: ActiveLoad) {
    activity?.navigateToDestinationRouteActivity(activeLoad)
}

fun Activity.navigateToInvoiceActivity(activeLoad: ActiveLoad) {
    startActivity(Intent(this, InvoiceActivity::class.java).apply {
        putExtra(IntentExtras.ACTIVE_LOAD, activeLoad)
    })
}

fun Fragment.navigateToInvoiceActivity(activeLoad: ActiveLoad) {
    activity?.navigateToInvoiceActivity(activeLoad)
}

fun Fragment.showLoadFilterBottomSheet(
    filter: LoadFilter? = null, listener: BookingFilterBottomSheet.FilterChangeListener
) {
    val fragment = BookingFilterBottomSheet
        .newInstance(filter, listener)
    fragment.show(fragmentManager!!, "")
}