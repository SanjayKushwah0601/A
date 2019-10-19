package com.freight.shipper.ui.invoice

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.freight.shipper.R
import com.freight.shipper.core.platform.BaseActivity
import com.freight.shipper.databinding.ActivityInvoiceBinding
import com.freight.shipper.utils.customviews.SignatureView
import kotlinx.android.synthetic.main.activity_invoice.*

class InvoiceActivity : BaseActivity() {

    private lateinit var binding: ActivityInvoiceBinding
    private lateinit var mSignature: SignatureView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
    }

    private fun initUI() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_invoice)

        mSignature = SignatureView(this@InvoiceActivity, null, scrollView)
        mSignature.setBackgroundColor(Color.TRANSPARENT)
        // Dynamically generating Layout through java code
        signatureLayout.addView(
            mSignature, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
    }
}
