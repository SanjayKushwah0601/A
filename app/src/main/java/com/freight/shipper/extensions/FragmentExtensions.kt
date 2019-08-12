package com.freight.shipper.extensions

import android.view.View
import androidx.fragment.app.Fragment
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