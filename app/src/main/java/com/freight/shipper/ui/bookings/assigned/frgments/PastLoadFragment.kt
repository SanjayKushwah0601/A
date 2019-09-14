package com.freight.shipper.ui.bookings.assigned.frgments

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.freight.shipper.model.PastLoad
import com.freight.shipper.ui.bookings.assigned.pager.LoadListFragment
import com.freight.shipper.ui.bookings.assigned.pager.PastLoadAdapter
import com.freight.shipper.ui.bookings.assigned.pager.PastLoadEventListener


class PastLoadFragment : LoadListFragment<PastLoad>(),
    PastLoadEventListener {
    //region - Companion
    companion object {
        fun newInstance(title: String) = PastLoadFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_TITLE, title)
            }
        }
    }
    //endregion

    //region - Private functions
    private val imageRowAdapter = PastLoadAdapter()
    //endregion

    //region - Public functions
    override fun getTabTitle(): String = arguments?.getString(ARG_TITLE) ?: ""

    override fun initRecyclerViewAdapter(): RecyclerView.Adapter<out RecyclerView.ViewHolder> {
        imageRowAdapter.clickListener = this
        return imageRowAdapter
    }

    override fun initRecyclerViewLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(context)
    }

    override fun setAdapterItems(items: List<PastLoad>?) {
        imageRowAdapter.imageList?.clear()
        imageRowAdapter.imageList = items?.toMutableList()
    }

    override fun fetchItem(): LiveData<List<PastLoad>>? {
        return viewmodel.pastLoads
    }

    override fun removeItemFromAdapter(position: Int) {
        imageRowAdapter.removeAt(position)
    }

    override fun setEditMode(editing: Boolean) {
        imageRowAdapter.editing = editing
    }

    /*override fun onWorkClicked(image: PastLoad) {
//        val imageIds = imageRowAdapter.imageList?.map { it.id } ?: listOf(image).map { it.id }
//        navigateToImageDetailScreen(image, null, imageIds, REQUEST_CODE_IMAGE_DETAIL)
    }

    override fun onDeleteClicked(image: PastLoad, viewHolder: ActiveLoadViewHolder) {
        removeItemFromAdapter(viewHolder.adapterPosition)
        viewmodel.unFavorite(viewHolder, image)
    }*/
    //endregion
}