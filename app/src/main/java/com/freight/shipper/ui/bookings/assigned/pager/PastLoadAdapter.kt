package com.freight.shipper.ui.bookings.assigned.pager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.freight.shipper.R
import com.freight.shipper.core.persistence.network.response.PastLoad

class PastLoadAdapter : RecyclerView.Adapter<PastLoadViewHolder>() {

    var editing = false
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var imageList: MutableList<PastLoad>? = null
        set(value) {
            field = value
//            field = value?.sortedBy { it.name?.toLowerCase() }?.toMutableList()
            notifyDataSetChanged()
        }

    var clickListener: PastLoadEventListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PastLoadViewHolder {
        val view = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            R.layout.view_past_load, parent, false
        )
        return PastLoadViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageList?.size ?: 0
    }

    override fun onBindViewHolder(holder: PastLoadViewHolder, position: Int) {
        holder.bind(imageList?.get(position), clickListener)
    }

    fun getItemAt(position: Int): PastLoad? {
        return imageList?.getOrNull(position)
    }

    fun removeAt(position: Int) {
        imageList?.removeAt(position)
        notifyItemRemoved(position)
    }
}

interface PastLoadEventListener {
    fun onInvoiceLinkClick(pastLoad: PastLoad)
}