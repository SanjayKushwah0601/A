package com.freight.shipper.ui.invoice

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.freight.shipper.R
import com.freight.shipper.core.platform.BaseActivity
import com.freight.shipper.databinding.ActivityInvoiceBinding

class InvoiceActivity : BaseActivity() {

    private lateinit var binding: ActivityInvoiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
    }

    private fun initUI() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_invoice)
    }
}
