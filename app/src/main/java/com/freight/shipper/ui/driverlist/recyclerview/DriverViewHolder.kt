package com.freight.shipper.ui.driverlist.recyclerview

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.freight.shipper.BR
import com.freight.shipper.core.persistence.network.response.Driver


/**
 * @CreatedBy Sanjay Kushwah on 9/22/2019.
 * sanjaykushwah0601@gmail.com
 */
class DriverViewHolder(private val dataBinding: ViewDataBinding) :
    RecyclerView.ViewHolder(dataBinding.root) {

    fun bind(driver: Driver?, clickListener: DriverClickListener?) {
        dataBinding.setVariable(BR.driver, driver)
        dataBinding.setVariable(BR.clickListener, clickListener)
        dataBinding.setVariable(BR.viewholder, this)
        dataBinding.executePendingBindings()
    }
}