package com.freight.shipper.ui.vehiclelist.recyclerview

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.freight.shipper.BR
import com.freight.shipper.core.persistence.network.response.Vehicle


/**
 * @CreatedBy Sanjay Kushwah on 9/22/2019.
 * sanjaykushwah0601@gmail.com
 */
class VehicleViewHolder(private val dataBinding: ViewDataBinding) :
    RecyclerView.ViewHolder(dataBinding.root) {

    fun bind(vehicle: Vehicle?, clickListener: VehicleClickListener?) {
        dataBinding.setVariable(BR.vehicle, vehicle)
        dataBinding.setVariable(BR.clickListener, clickListener)
        dataBinding.setVariable(BR.viewholder, this)
        dataBinding.executePendingBindings()
    }
}