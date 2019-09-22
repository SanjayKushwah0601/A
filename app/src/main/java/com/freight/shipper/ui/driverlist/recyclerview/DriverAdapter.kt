package com.freight.shipper.ui.driverlist.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.freight.shipper.R
import com.freight.shipper.core.persistence.network.response.Driver

/**
 * @CreatedBy Sanjay Kushwah on 9/22/2019.
 * sanjaykushwah0601@gmail.com
 */
class DriverAdapter(
    private val list: MutableList<Driver> = mutableListOf(),
    private val clickListener: DriverClickListener? = null
) : RecyclerView.Adapter<DriverViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriverViewHolder {
        return DriverViewHolder(
            DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(parent.context),
                R.layout.view_driver, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: DriverViewHolder, position: Int) {
        holder.bind(list[position], clickListener)
    }

    fun getItemAt(position: Int): Driver? {
        return list.getOrNull(position)
    }

    fun removeAt(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addListItems(newItems: List<Driver>) {
        list.clear()
        list.addAll(newItems)
        notifyDataSetChanged()
    }
}

interface DriverClickListener {
    fun onDriverClick(position: Int, load: Driver)
}