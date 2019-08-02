package com.freight.shipper.extensions

import android.app.Activity
import android.content.Intent
import com.freight.shipper.ui.authentication.login.LoginActivity


/**
 * @CreatedBy Sanjay Kushwah on 8/2/2019.
 * sanjaykushwah0601@gmail.com
 */

fun Activity.startLoginActivity() {
    startActivity(Intent(this, LoginActivity::class.java))
}