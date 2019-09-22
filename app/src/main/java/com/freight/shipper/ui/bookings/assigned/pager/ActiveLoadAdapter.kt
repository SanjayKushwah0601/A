package com.freight.shipper.ui.bookings.assigned.pager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.freight.shipper.R
import com.freight.shipper.core.persistence.network.response.ActiveLoad

class ActiveLoadAdapter : RecyclerView.Adapter<ActiveLoadViewHolder>() {

    var editing = false
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var imageList: MutableList<ActiveLoad>? = null
        set(value) {
            field = value
//            field = value?.sortedBy { it.name?.toLowerCase() }?.toMutableList()
            notifyDataSetChanged()
        }

    var clickListener: LoadEventListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActiveLoadViewHolder {
        val view = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            R.layout.view_active_load, parent, false
        )
        return ActiveLoadViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageList?.size ?: 0
    }

    override fun onBindViewHolder(holder: ActiveLoadViewHolder, position: Int) {
        holder.bind(imageList?.get(position), clickListener)
    }

    fun getItemAt(position: Int): ActiveLoad? {
        return imageList?.getOrNull(position)
    }

    fun removeAt(position: Int) {
        imageList?.removeAt(position)
        notifyItemRemoved(position)
    }
}

interface LoadEventListener {
    fun onWorkClicked(image: ActiveLoad) {}
//    fun onDeleteClicked(image: Image, viewHolder: ActiveLoadViewHolder) {}
}