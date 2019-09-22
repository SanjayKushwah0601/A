package com.freight.shipper.ui.bookings.newload.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.freight.shipper.R
import com.freight.shipper.core.persistence.network.response.NewLoad

class NewLoadAdapter(
    private val list: MutableList<NewLoad> = mutableListOf(),
    private val clickListener: NewLoadEventListener? = null
) : RecyclerView.Adapter<NewLoadViewHolder>() {

    var editing = false
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewLoadViewHolder {
        return NewLoadViewHolder(
            DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(parent.context),
                R.layout.view_new_load, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: NewLoadViewHolder, position: Int) {
        holder.bind(list[position], clickListener)
    }

    fun getItemAt(position: Int): NewLoad? {
        return list.getOrNull(position)
    }

    fun removeAt(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addListItems(newItems: List<NewLoad>) {
        list.clear()
        list.addAll(newItems)
        notifyDataSetChanged()
    }
}

interface NewLoadEventListener {
    fun onAcceptLoad(position: Int, load: NewLoad) {}
    fun onCounterLoad(position: Int, load: NewLoad) {}
//    fun onDeleteClicked(image: Image, viewHolder: ActiveLoadViewHolder) {}
}