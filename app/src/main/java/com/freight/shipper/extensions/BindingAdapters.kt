package com.freight.shipper.extensions

import android.view.View
import androidx.databinding.BindingAdapter


/**
 * @CreatedBy Sanjay Kushwah on 8/25/2019.
 * sanjaykushwah0601@gmail.com
 */

@BindingAdapter("app:manageVisibility")
fun View.manageVisibility(isVisible: Boolean) {
    setVisibleIf { isVisible }
}