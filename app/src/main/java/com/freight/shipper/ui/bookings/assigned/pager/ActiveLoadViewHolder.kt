package com.freight.shipper.ui.bookings.assigned.pager

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.freight.shipper.BR
import com.freight.shipper.core.persistence.network.response.ActiveLoad

/**
 * View Holder implementation for a RecyclerView that displays work items
 */
class ActiveLoadViewHolder(private val dataBinding: ViewDataBinding) :
    RecyclerView.ViewHolder(dataBinding.root) {

    fun bind(image: ActiveLoad?, clickListener: LoadEventListener?) {
        dataBinding.setVariable(BR.activeLoad, image)
        dataBinding.setVariable(BR.clickListener, clickListener)
        dataBinding.setVariable(BR.viewholder, this)
        dataBinding.executePendingBindings()
    }
}