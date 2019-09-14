package com.freight.shipper.ui.bookings.newload.recyclerview

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.freight.shipper.BR
import com.freight.shipper.model.NewLoad


/**
 * @CreatedBy Sanjay Kushwah on 9/14/2019.
 * sanjaykushwah0601@gmail.com
 */
class NewLoadViewHolder(private val dataBinding: ViewDataBinding) :
    RecyclerView.ViewHolder(dataBinding.root) {

    fun bind(image: NewLoad?, clickListener: NewLoadEventListener?) {
        dataBinding.setVariable(BR.loadModel, image)
        dataBinding.setVariable(BR.clickListener, clickListener)
        dataBinding.setVariable(BR.viewholder, this)
        dataBinding.executePendingBindings()
    }
}