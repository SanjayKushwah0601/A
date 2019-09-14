package com.freight.shipper.ui.bookings.assigned.pager

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.freight.shipper.BR
import com.freight.shipper.model.PastLoad

/**
 * View Holder implementation for a RecyclerView that displays work items
 */
class PastLoadViewHolder(private val dataBinding: ViewDataBinding) :
    RecyclerView.ViewHolder(dataBinding.root) {

    fun bind(image: PastLoad?, clickListener: PastLoadEventListener?) {
        dataBinding.setVariable(BR.pastLoad, image)
        dataBinding.setVariable(BR.clickListener, clickListener)
        dataBinding.setVariable(BR.viewholder, this)
        dataBinding.executePendingBindings()
    }
}