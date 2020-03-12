package com.freight.shipper.ui.invoice

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.freight.shipper.FreightApplication
import com.freight.shipper.R
import com.freight.shipper.core.persistence.network.response.ActiveLoad
import com.freight.shipper.core.platform.BaseActivity
import com.freight.shipper.core.platform.BaseViewModelFactory
import com.freight.shipper.databinding.ActivityInvoiceBinding
import com.freight.shipper.extensions.navigateToDashboard
import com.freight.shipper.extensions.showErrorMessage
import com.freight.shipper.extensions.showSingleOptionAlertDialog
import com.freight.shipper.model.IntentExtras
import com.freight.shipper.repository.RouteRepository
import com.freight.shipper.ui.bookings.assigned.LoadPagerFragment
import com.freight.shipper.ui.dashboard.DashboardActivity
import com.rm.freedrawview.FreeDrawView
import kotlinx.android.synthetic.main.activity_invoice.*
import java.io.File
import java.io.FileOutputStream

class InvoiceActivity : BaseActivity() {

    // region - Private properties
    private lateinit var binding: ActivityInvoiceBinding
    private val viewModel: InvoiceViewModel by lazy {
        ViewModelProviders.of(this, BaseViewModelFactory {
            InvoiceViewModel(
                intent.getParcelableExtra(IntentExtras.ACTIVE_LOAD) as ActiveLoad,
                RouteRepository(
                    FreightApplication.instance.api, FreightApplication.instance.loginManager
                )
            )
        }).get(InvoiceViewModel::class.java)
    }
    // endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
        setupObservers()
    }

    // region - Private functions
    private fun setupObservers() {
        viewModel.loadDetail.observe(this, Observer { invoiceResponse ->
            binding.viewModel = viewModel
            binding.loadDetail = invoiceResponse
            binding.executePendingBindings()
        })

        viewModel.error.observe(this, Observer { errorMsg ->
            showErrorMessage(errorMsg)
        })

        viewModel.submitInvoice.observe(this, Observer {

            // This will take a screenshot of the current drawn content of the view
            freeDraw.getDrawScreenshot(object : FreeDrawView.DrawCreatorListener {
                override fun onDrawCreated(draw: Bitmap) { // The draw Bitmap is the drawn content of the View
//                    uploadSignImage(podModel.docketDeliveryMergeId!!, getFileFromBitmap(draw, name))
                    getFileFromBitmap(draw, it.first)?.also { file ->
                        viewModel.uploadSignature(it.second, file)
                    }
                }

                override fun onDrawCreationError() { // Something went wrong creating the bitmap, should never
// happen unless the async task has been canceled
                    showErrorMessage(getString(R.string.cannot_take_sign))

                }
            })
//            val signatureFile = mSignature.print(it.first)
//            if (signatureFile != null) {
//                viewModel.uploadSignature(it.second, signatureFile)
//            } else {
//                showErrorMessage(getString(R.string.cannot_take_sign))
//            }
        })

        viewModel.submitInvoiceResponse.observe(this, Observer {
            showSingleOptionAlertDialog(
                R.string.alert_title, R.string.invoice_success_message, cancelable = false
            ) {
                navigateToDashboard(
                    DashboardActivity.START_SCREEN_ASSIGNED, LoadPagerFragment.PAST_LOAD
                )
            }
        })
    }

    private fun initUI() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_invoice)
        binding.viewModel = viewModel
        binding.executePendingBindings()

        btnUndo?.setOnClickListener {
            freeDraw?.clearDrawAndHistory()
//            freeDraw?.undoLast()
//            if (freeDraw.getPathCount(true) == 0)
//                buttonSignImage.isEnabled = false
        }
    }

    private fun getFileFromBitmap(bitmap: Bitmap, fileName: String): File? {
        return try {
            val defaultFile = File(filesDir.absolutePath + "/${getString(R.string.app_name)}")
            if (!defaultFile.exists()) defaultFile.mkdirs()
            var file = File(defaultFile, "$fileName.jpg")
            if (file.exists()) {
                file.delete()
                file = File(defaultFile, "$fileName.jpg")
            }
            val output = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, output)
            output.flush()
            output.close()
//            dialog.dismiss()
            file
        } catch (e: Exception) {
            e.printStackTrace()
//            dialog.dismiss()
            Toast.makeText(baseContext, "Failed", Toast.LENGTH_SHORT).show()
            null
        }
    }
    // endregion
}
