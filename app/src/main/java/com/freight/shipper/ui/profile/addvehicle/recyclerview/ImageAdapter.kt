package com.freight.shipper.ui.profile.addvehicle.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.freight.shipper.R
import com.freight.shipper.core.persistence.network.response.Image

/**
 * RecyclerView adapter implementation for a RecyclerView that displays image items
 */

open class ImageAdapter(
    open var clickListener: ImageClickListener,
    hideDeleteButtons: Boolean = true,
    val hideAddButton: Boolean = true
) : RecyclerView.Adapter<ImageViewHolder>() {
//    constructor(clickListener: ImageClickListener) : this(clickListener, false, true)

    var headerCount = if (hideAddButton) {
        0
    } else {
        1
    }

    var hideDeleteButtons: Boolean = hideDeleteButtons
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var artworks: MutableList<Image> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            R.layout.view_item_artwork_tile, parent, false
        )
        return ImageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return artworks.size + headerCount
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        if (!hideAddButton && position == 0) {
            holder.bindAsAddItem(clickListener)
        } else {
            holder.bind(
                artworks[position - headerCount],
                clickListener,
                hideDeleteButtons
            )
        }
    }

    override fun onViewRecycled(holder: ImageViewHolder) {
        holder.unbind()
    }

//    override fun getItemId(position: Int): Long {
//        return artworks[position].id
//    }

    fun addImages(imagesToAdd: List<Image>?) {
        var index = 0

        imagesToAdd?.forEach {
            if (!artworks.contains(it)) {
                artworks.add(index, it)
            }
            index++
        }

        notifyDataSetChanged()
    }

    fun removeImage(adapterPosition: Int) {
        val itemPosition = adapterPosition - headerCount
        if (adapterPosition >= 0 && itemPosition < (artworks.size)) {
            artworks.removeAt(itemPosition)
        }
        notifyDataSetChanged()
    }

//    fun removeImageWithId(imageId: Long?) {
//        artworks.removeAll { it.uri == imageId }
//
//        notifyDataSetChanged()
//    }

    fun addImage(image: Image, position: Int = 0) {
        if (artworks.contains(image)) {
            return
        }
        val itemPosition = position //+ headerCount
        artworks.add(itemPosition, image)
//        if (itemPosition >= 0 && itemPosition < artworks.size) {
//            artworks.add(itemPosition, image)
//        }
            notifyDataSetChanged()
    }

    fun reset() {
        artworks.clear()
        notifyDataSetChanged()
    }
}

interface ImageClickListener {
    fun onImageClicked(image: Image)
    fun onDeleteButtonClicked(image: Image, position: Int)
    fun onNewItemClicked()
}