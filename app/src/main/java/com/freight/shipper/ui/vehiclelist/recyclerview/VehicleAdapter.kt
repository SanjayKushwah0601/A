package com.freight.shipper.ui.vehiclelist.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.freight.shipper.R
import com.freight.shipper.core.persistence.network.response.Vehicle

/**
 * @CreatedBy Sanjay Kushwah on 9/22/2019.
 * sanjaykushwah0601@gmail.com
 */
class VehicleAdapter(
    private val list: MutableList<Vehicle> = mutableListOf(),
    private val clickListener: VehicleClickListener? = null
) : RecyclerView.Adapter<VehicleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        return VehicleViewHolder(
            DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(parent.context),
                R.layout.view_vehicle, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        holder.bind(list[position], clickListener)
    }

    fun getItemAt(position: Int): Vehicle? {
        return list.getOrNull(position)
    }

    fun removeAt(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addListItems(newItems: List<Vehicle>) {
        list.clear()
        list.addAll(newItems)
        notifyDataSetChanged()
    }
}

interface VehicleClickListener {
    fun onVehicleClick(position: Int, load: Vehicle)
}