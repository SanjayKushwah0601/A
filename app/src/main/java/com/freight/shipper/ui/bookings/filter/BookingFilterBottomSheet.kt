package com.freight.shipper.ui.bookings.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.freight.shipper.R
import com.freight.shipper.model.IntentExtras
import com.freight.shipper.model.LoadFilter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottomsheet_load_filter.*


/**
 * @CreatedBy Sanjay Kushwah on 12/1/2019.
 * sanjaykushwah0601@gmail.com
 */
class BookingFilterBottomSheet : BottomSheetDialogFragment() {

    private var filter: LoadFilter? = null
    private var listener: FilterChangeListener? = null

    companion object {
        fun newInstance(
            filter: LoadFilter?, listener: FilterChangeListener
        ): BookingFilterBottomSheet {
            val bundle = Bundle().apply {
                putParcelable(IntentExtras.LOAD_FILTER, filter)
            }
            val fragment = BookingFilterBottomSheet().apply { arguments = bundle }
            fragment.listener = listener
            return fragment
        }
    }

    // region - Lifecycle functions
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottomsheet_load_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filter = arguments?.getParcelable(IntentExtras.LOAD_FILTER)
        initUI(filter)
    }

    //    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppBottomSheetDialogTheme)
//    }
    override fun getTheme(): Int = R.style.BaseBottomSheetDialog
    // endregion

    // region - Private functions
    private fun initUI(filter: LoadFilter?) {
        preFillFilter(filter)
        distanceSeek?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                distance?.text = "$p1"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })
        buttonApply?.setOnClickListener {
            val filterToApply = filter ?: LoadFilter()
            filterToApply.weightFrom = etWeightF.text.toString().toIntOrNull()
            filterToApply.weightTo = etWeightT.text.toString().toIntOrNull()
            filterToApply.distance = distance.text.toString().toIntOrNull()
            listener?.onFilterChange(filterToApply)
            this@BookingFilterBottomSheet.dismiss()
        }
    }

    private fun preFillFilter(filter: LoadFilter?) {
        filter?.distance?.also {
            distance?.text = "$it"

            distanceSeek?.progress = 0 // call these two methods before setting progress.
            distanceSeek?.max = 100
            distanceSeek?.progress = it
        }
        filter?.weightFrom?.also { etWeightF?.setText(it.toString()) }
        filter?.weightTo?.also { etWeightT?.setText(it.toString()) }
    }
    // endregion

    interface FilterChangeListener {
        fun onFilterChange(filter: LoadFilter)
        fun onClearFilter()
    }
}