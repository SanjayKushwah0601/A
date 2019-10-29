package com.freight.shipper.extensions

import android.content.Context
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.Spinner
import com.freight.shipper.core.platform.HintSpinnerAdapter
import com.freight.shipper.core.platform.HintSpinnerAdapter1

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
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (shown) {
        inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    } else {
        inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
    }
}

fun <T> Spinner.setOnItemSelectListener(
    adapter: HintSpinnerAdapter<T>,
    listener: (position: T) -> Unit
) {
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(p0: AdapterView<*>?) {}
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            if (p2 - 1 != -1) {
                listener(adapter.getItemAtPosition(p2 - 1))
            }
        }
    }
}

fun <T> Spinner.onItemSelectedListenerWithoutLabel(
    adapter: HintSpinnerAdapter1<T>,
    listener: (position: T) -> Unit
) {
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(p0: AdapterView<*>?) {}
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            if (p2 >= 0) {
//                val selected = p0?.getItemAtPosition(p2).toString()
//                itemSelected?.invoke(selected)
                listener(adapter.getItemAtPosition(p2))
            }
        }
    }
}
