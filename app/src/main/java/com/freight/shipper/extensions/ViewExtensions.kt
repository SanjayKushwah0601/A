package com.freight.shipper.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.freight.shipper.R
import com.google.android.material.snackbar.Snackbar

/**
 * Do some work on a view only after it is measured
 *
 * Views do not get a nonzero height or width until after a global layout step is completed, so
 * even if the eventual height will be nonzero, View.getHeight() will return zero until that time.
 *
 * This extension will run a block of code only if the view has been measured, otherwise it waits
 * for a layout pass to occur on the view before running the code
 */
inline fun <T : View> T.afterMeasured(crossinline doWork: T.() -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            //Clean up the listener to avoid memory leaks!
            viewTreeObserver.removeOnGlobalLayoutListener(this)

            doWork()
        }
    })

    this.invalidate()
}

fun View.setVisibleIf(predicate: () -> Boolean) {
    visibility = if (predicate()) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun View.setHiddenIf(predicate: () -> Boolean) {
    visibility = if (predicate()) {
        View.GONE
    } else {
        View.VISIBLE
    }
}


fun View.setKeyboardShown(context: Context, shown: Boolean) {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (shown) {
        inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    } else {
        inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
    }
}
