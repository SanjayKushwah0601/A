package com.freight.shipper.ui.splash

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.freight.shipper.FreightApplication
import com.freight.shipper.R
import com.freight.shipper.extensions.navigateToDashboard
import com.freight.shipper.extensions.setImageDrawableGlide
import com.freight.shipper.extensions.startLoginActivity
import kotlinx.android.synthetic.main.activity_main.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        image?.setImageDrawableGlide(resources.getDrawable(R.drawable.splash))
        Handler().postDelayed({
            if (FreightApplication.instance.loginManager.getToken() != null) {
                navigateToDashboard()
            } else {
                startLoginActivity()
            }
            finish()
        }, 3000)
    }
}
