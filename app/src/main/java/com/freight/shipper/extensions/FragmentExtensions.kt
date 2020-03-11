package com.freight.shipper.extensions

import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.freight.shipper.R
import com.freight.shipper.model.ButtonClickAction
import com.google.android.material.snackbar.Snackbar


/**
 * @CreatedBy Sanjay Kushwah on 8/12/2019.
 * sanjaykushwah0601@gmail.com
 */

fun Fragment.showErrorMessage(error: String, view: View? = getView(), duration: Int = Snackbar.LENGTH_LONG): Snackbar? {
    return activity?.showErrorMessage(error, view, duration)
}

fun Fragment.showConfirmationMessage(
    message: String, view: View? = getView(),
    duration: Int = Snackbar.LENGTH_LONG
): Snackbar? {
    return activity?.showConfirmationMessage(message, view, duration)
}

fun Fragment.showSingleOptionAlertDialog(title: String? = null,
                                         message: String? = null,
                                         @StringRes buttonTextRes: Int = R.string.ok,
                                         cancelable: Boolean = true,
                                         cancelTextRes: Int? = null,
                                         dissmissListenerClick: (() -> Unit)? = null,
                                         buttonClickAction: ((clickAction: ButtonClickAction) -> Unit)? = null) {
    activity?.showSingleOptionAlertDialog(title, message, buttonTextRes, cancelable, cancelTextRes,
        dismissListener = dissmissListenerClick, buttonClickAction = buttonClickAction)
}

fun Fragment.showSingleOptionAlertDialog(@StringRes titleRes: Int? = null,
                                         @StringRes messageRes: Int? = null,
                                         @StringRes buttonTextRes: Int = R.string.ok,
                                         cancelable: Boolean = true,
                                         cancelTextRes: Int? = null,
                                         negativeBtnClick: (() -> Unit)? = null,
                                         buttonClickAction: ((clickAction: ButtonClickAction) -> Unit)? = null) {
    activity?.showSingleOptionAlertDialog(titleRes, messageRes, buttonTextRes, cancelable, cancelTextRes,
        dismissListener = negativeBtnClick, buttonClickAction = buttonClickAction)
}