package com.freight.shipper.extensions

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.freight.shipper.R
import com.google.android.material.snackbar.Snackbar


/**
 * @CreatedBy Sanjay Kushwah on 8/10/2019.
 * sanjaykushwah0601@gmail.com
 */

//A default way to show error messages in an activity, if nothing else exists
fun Activity.showErrorMessage(error: String, view: View? = null, duration: Int = Snackbar.LENGTH_LONG): Snackbar? {
    return showErrorMessage(error, view, duration, null, null)
}

fun Activity.showErrorMessage(
    error: String, view: View? = null, duration: Int = Snackbar.LENGTH_LONG,
    detailText: String?, detailAction: ((View) -> Unit)?
): Snackbar? {
    var defaultView = view

    if (view == null) {
        defaultView = findViewById(android.R.id.content) ?: return null
    }

    val snackbar: Snackbar?

    if (defaultView != null) {
        snackbar = Snackbar.make(defaultView, error, duration)
        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(ContextCompat.getColor(this, R.color.error_message_bg))

        val textView = snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.setTextColor(Color.WHITE)

        snackbar.setAction(detailText, detailAction)
        snackbar.setActionTextColor(Color.WHITE)

        snackbar.show()
    } else {
        snackbar = null
    }

    return snackbar
}

fun Activity.showConfirmationMessage(
    error: String, view: View? = null, duration: Int = Snackbar.LENGTH_LONG
): Snackbar? {
    var contentView = view

    if (view == null) {
        contentView = findViewById(android.R.id.content) ?: return null
    }

    val snackbar: Snackbar?
    if (contentView != null) {
        snackbar = Snackbar.make(contentView, error, duration)
        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(resources.getColor(R.color.confirmation_message_bg))
        val textView = snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.setTextColor(Color.WHITE)
        snackbar.show()
    } else {
        snackbar = null
    }

    return snackbar
}