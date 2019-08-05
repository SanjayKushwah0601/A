package com.freight.shipper.ui.splash

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.freight.shipper.FreightApplication
import com.freight.shipper.R
import com.freight.shipper.extensions.navigateToDashboard
import com.freight.shipper.extensions.startLoginActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler().postDelayed({
            if (FreightApplication.instance.loginManager.getToken() != null) {
                navigateToDashboard()
            } else {
                startLoginActivity()
            }
            finish()
        }, 100)
    }
}
