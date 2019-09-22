package com.freight.shipper.ui.addvehicle.recyclerview

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.freight.shipper.BR
import com.freight.shipper.R
import com.freight.shipper.core.persistence.network.response.Image

/**
 * View Holder implementation for a RecyclerView that displays image items
 */

class ImageViewHolder(val itemRowBinding: ViewDataBinding) :
    RecyclerView.ViewHolder(itemRowBinding.root) {

    private val artworkView: ImageView? = itemRowBinding.root.findViewById(R.id.artworkView)
    private val container = itemRowBinding.root

    fun bind(
        image: Image?,
        clickListener: ImageClickListener?,
        hideDeleteButton: Boolean
    ) {

        itemRowBinding.setVariable(BR.imageModel, image)
        itemRowBinding.setVariable(BR.clickListener, clickListener)
        itemRowBinding.setVariable(BR.hideDeleteButton, hideDeleteButton)
        itemRowBinding.setVariable(BR.position, adapterPosition)
        itemRowBinding.setVariable(BR.isFirstPosition, false)
        itemRowBinding.executePendingBindings()
        setImage(image)
    }

    fun unbind() {
        Glide.with(artworkView?.context).clear(artworkView)
    }

    fun bindAsAddItem(clickListener: ImageClickListener) {
        itemRowBinding.setVariable(BR.clickListener, clickListener)
        itemRowBinding.setVariable(BR.infoButton, true)
        itemRowBinding.setVariable(BR.hideDeleteButton, true)
        itemRowBinding.setVariable(BR.isFirstPosition, true)
        itemRowBinding.executePendingBindings()

        artworkView?.let {
            it.setColorFilter(ContextCompat.getColor(artworkView.context, R.color.add_item_tint))
            it.setImageResource(R.drawable.ic_add_circle_outline)
            it.scaleType = ImageView.ScaleType.CENTER
        }
    }

    private fun setImage(image: Image?) {
        if (image != null) {
            container.visibility = View.VISIBLE
            artworkView?.setImageURI(image.uri)
        } else {
            container.visibility = View.GONE
        }
    }
}