package com.freight.shipper.extensions

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
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
fun Activity.showErrorMessage(
    error: String,
    view: View? = null,
    duration: Int = Snackbar.LENGTH_LONG
): Snackbar? {
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

        val textView =
            snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
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
        val textView =
            snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.setTextColor(Color.WHITE)
        snackbar.show()
    } else {
        snackbar = null
    }

    return snackbar
}

fun Activity.showImageChooser(requestCode: Int) {
    val intent = Intent()
    intent.type = "image/*"
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        intent.action = Intent.ACTION_OPEN_DOCUMENT
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
    } else {
        intent.action = Intent.ACTION_GET_CONTENT
    }
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

    //Make sure to only display images that can be opened as an inputstream so we can upload them
    intent.addCategory(Intent.CATEGORY_OPENABLE)

    //Multiple selection flag is only supported on v18+
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
    }

    startActivityForResult(intent, requestCode)
}