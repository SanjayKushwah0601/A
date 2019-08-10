package com.freight.shipper.extensions

import android.graphics.Color
import android.graphics.PorterDuff
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.freight.shipper.R


/**
 * @CreatedBy Sanjay Kushwah on 8/4/2019.
 * sanjaykushwah0601@gmail.com
 */

fun AppCompatActivity.setupToolbar(
    toolbar: Toolbar, darkIcons: Boolean = false,
    enableUpButton: Boolean = true, title: String? = null,
    subTitle: String? = null
) {
    setupToolbarWithTitle(toolbar, darkIcons, enableUpButton, title, subTitle)
}

fun AppCompatActivity.setupToolbarWithTitle(
    toolbar: Toolbar, darkIcons: Boolean = false,
    enableUpButton: Boolean = true, title: String? = null,
    subTitle: String? = null
) {
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(enableUpButton)
//    val indicator = if (darkIcons) R.drawable.ic_arrow_back_dark else R.drawable.ic_arrow_back_white
//    supportActionBar?.setHomeAsUpIndicator(indicator)
    if (darkIcons) toolbar.navigationIcon?.mutate()?.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN)
    else toolbar.navigationIcon?.mutate()?.setColorFilter(
        resources.getColor(R.color.colorAccent),
        PorterDuff.Mode.SRC_IN
    )
    supportActionBar?.title = title ?: ""
    if (!subTitle.isNullOrEmpty()) supportActionBar?.subtitle = subTitle
}