package com.freight.shipper.ui.bookings.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.UiThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.freight.shipper.FreightApplication
import com.freight.shipper.R
import com.freight.shipper.core.persistence.network.client.server.MeuralAPIContract
import com.freight.shipper.core.platform.BaseViewModelFactory
import com.freight.shipper.core.platform.NonNullObserver
import com.freight.shipper.extensions.setVisibleIf
import com.freight.shipper.repository.LoadRepository
import com.freight.shipper.ui.bookings.LoadPagerViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_load_list.*

public abstract class LoadListFragment<T> : Fragment() {
    companion object {
        const val REQUEST_CODE_PLAYLIST_DETAIL = 0
        const val REQUEST_CODE_ARTIST_DETAIL = 1
        const val REQUEST_CODE_IMAGE_DETAIL = 2
        const val ARG_TITLE = "title"
    }

    open val meuralAPI: MeuralAPIContract by lazy { FreightApplication.instance.meuralAPI }
    open lateinit var adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>
    open var errorSnackbar: Snackbar? = null
    val viewmodel: LoadPagerViewModel by lazy {
        ViewModelProviders.of(this,
            BaseViewModelFactory {
                LoadPagerViewModel(
                    LoadRepository(
                        FreightApplication.instance.meuralAPI,
                        FreightApplication.instance.loginManager
                    )
                )
            }).get(LoadPagerViewModel::class.java)
    }

    abstract fun getTabTitle(): String
    abstract fun fetchItem(): LiveData<List<T>>?
    abstract fun initRecyclerViewAdapter(): RecyclerView.Adapter<out RecyclerView.ViewHolder>
    abstract fun initRecyclerViewLayoutManager(): RecyclerView.LayoutManager
    @UiThread
    abstract fun setAdapterItems(items: List<T>?)

    abstract fun removeItemFromAdapter(position: Int)
    abstract fun setEditMode(editing: Boolean)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_load_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = initRecyclerViewAdapter()
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = initRecyclerViewLayoutManager()
        setObservers()
        swipeRefreshLayout?.setOnRefreshListener { fetchItem() }
    }

    override fun onResume() {
        super.onResume()
        fetchItem()?.observe(this, object : NonNullObserver<List<T>>() {
            override fun onNonNullChanged(value: List<T>) {
                errorSnackbar?.dismiss()
                setAdapterItems(value)
                swipeRefreshLayout?.isRefreshing = false
                setEmptyViewVisibility()
            }
        })
    }

    private fun setObservers() {
        /*viewmodel.getUnFavoriteLiveData().observe(this, object : NonNullObserver<Boolean>() {
            override fun onNonNullChanged(value: Boolean) {
                setEmptyViewVisibility()
                if (!value) {
                    errorSnackbar = showErrorMessage(
                        getString(R.string.error_removing_item),
                        duration = Snackbar.LENGTH_INDEFINITE
                    )
                }
            }
        })*/

        /*viewmodel.getErrorLiveData().observe(this, object : NonNullObserver<APIError>() {
            override fun onNonNullChanged(value: APIError) {
                setEmptyViewVisibility()
                errorSnackbar = showErrorMessage(
                    getString(R.string.failed_to_fetch_load),
                    duration = Snackbar.LENGTH_INDEFINITE
                )
            }
        })*/
    }

    private fun setEmptyViewVisibility() {
        emptyView?.setVisibleIf { adapter.itemCount == 0 }
    }
}