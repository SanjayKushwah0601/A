package com.freight.shipper.ui.bookings.counterdialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.freight.shipper.FreightApplication
import com.freight.shipper.R
import com.freight.shipper.core.persistence.network.result.APIResult
import com.freight.shipper.extensions.showConfirmationMessage
import com.freight.shipper.extensions.showErrorMessage
import com.freight.shipper.model.NewLoad
import kotlinx.android.synthetic.main.dialog_counter.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * @CreatedBy Sanjay Kushwah on 9/15/2019.
 * sanjaykushwah0601@gmail.com
 */
class CounterDialog : DialogFragment() {

    companion object {

        const val EXTRA_LOAD = "load"

        fun newInstance(newLoad: NewLoad): CounterDialog {
            return CounterDialog().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_LOAD, newLoad)
                }
            }
        }
    }

    private lateinit var load: NewLoad
    var listener: CounterListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        load = arguments?.getParcelable(EXTRA_LOAD) as NewLoad
        return inflater.inflate(R.layout.dialog_counter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        buttonCancel.setOnClickListener { dismiss() }
        buttonConfirm.setOnClickListener {
            val price = price.text.toString()
            if (!price.isNullOrEmpty()) {
                updatePrice(price)
            } else {
                requireActivity().showErrorMessage(getString(R.string.counter_price_enter_error))
            }
        }
    }

    private fun updatePrice(price: String) {
        GlobalScope.launch(Dispatchers.IO) {
            load.loadsId?.also {
                val result = FreightApplication.instance.api.addLoadOfferPrice(it, price)
                withContext(Dispatchers.Main) {
                    when (result) {
                        is APIResult.Success -> {
                            activity?.showConfirmationMessage(getString(R.string.offer_update_message))
                            listener?.onSuccess()
                        }
                        is APIResult.Failure -> {
                            activity?.showErrorMessage(getString(R.string.error_updating_offer_price))
                        }
                    }
                }
                dismiss()
            }
        }
    }

    interface CounterListener {
        fun onSuccess()
    }
}