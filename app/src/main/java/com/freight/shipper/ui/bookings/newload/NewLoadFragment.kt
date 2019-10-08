package com.freight.shipper.ui.bookings.newload

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.freight.shipper.FreightApplication
import com.freight.shipper.R
import com.freight.shipper.core.platform.BaseViewModelFactory
import com.freight.shipper.extensions.setVisibleIf
import com.freight.shipper.extensions.showConfirmationMessage
import com.freight.shipper.extensions.showCounterDialog
import com.freight.shipper.extensions.showErrorMessage
import com.freight.shipper.repository.LoadRepository
import com.freight.shipper.ui.bookings.assigned.pager.ActiveLoadEventListener
import com.freight.shipper.ui.bookings.counterdialog.CounterDialog
import com.freight.shipper.ui.bookings.newload.recyclerview.NewLoadAdapter
import com.freight.shipper.ui.bookings.newload.recyclerview.NewLoadEventListener
import com.freight.shipper.ui.dashboard.DashboardActivity
import kotlinx.android.synthetic.main.fragment_new_load_list.*
import timber.log.Timber


class NewLoadFragment : Fragment(),
    ActiveLoadEventListener {
    //region - Companion
    companion object {
        fun newInstance() = NewLoadFragment()
    }
    //endregion

    //region - Private functions
    val viewModel: NewLoadViewModel by lazy {
        ViewModelProviders.of(this,
            BaseViewModelFactory {
                NewLoadViewModel(
                    LoadRepository(
                        FreightApplication.instance.api,
                        FreightApplication.instance.loginManager
                    )
                )
            }).get(NewLoadViewModel::class.java)
    }
    private lateinit var adapterNewLoad: NewLoadAdapter
    //endregion

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_load_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterNewLoad = NewLoadAdapter(clickListener = viewModel as NewLoadEventListener)
        recyclerView?.adapter = adapterNewLoad
        recyclerView?.layoutManager = LinearLayoutManager(context)
        setObservers()
        swipeRefreshLayout?.setOnRefreshListener { viewModel.refreshNewLoad() }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshNewLoad()
    }

    private fun setObservers() {
        viewModel.newLoadsError.observe(this, Observer {
            viewModel.isLoading.postValue(false)
            swipeRefreshLayout.isRefreshing = false
            showErrorMessage(it)
            setEmptyViewVisibility()
        })
        viewModel.newLoads.observe(this, Observer {
            viewModel.isLoading.postValue(false)
            swipeRefreshLayout.isRefreshing = false
            adapterNewLoad.addListItems(it)
            setEmptyViewVisibility()
        })

        viewModel.acceptLoadResponse.observe(viewLifecycleOwner, Observer {
            viewModel.isLoading.postValue(false)
            showConfirmationMessage(it)
            (activity as DashboardActivity).navigateToActiveLoad()
        })
        viewModel.counterAction.observe(this, Observer {
            Timber.i("$it")
            showCounterDialog(it, object : CounterDialog.CounterListener {
                override fun onSuccess() {
                    // TODO: Navigate to active load screen
                    (activity as DashboardActivity).navigateToActiveLoad()
                }
            })
        })
        viewModel.error.observe(this, Observer {
            viewModel.isLoading.postValue(false)
            showErrorMessage(it)
        })
    }

    private fun setEmptyViewVisibility() {
        emptyView?.setVisibleIf { adapterNewLoad.itemCount == 0 }
    }
}