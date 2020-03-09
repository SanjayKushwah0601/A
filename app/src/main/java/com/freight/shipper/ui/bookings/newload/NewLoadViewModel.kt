package com.freight.shipper.ui.bookings.newload

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.freight.shipper.R
import com.freight.shipper.core.persistence.network.response.NewLoad
import com.freight.shipper.core.persistence.network.result.NetworkCallback
import com.freight.shipper.core.platform.ActionLiveData
import com.freight.shipper.core.platform.BaseViewModel
import com.freight.shipper.model.LoadFilter
import com.freight.shipper.repository.LoadRepository
import com.freight.shipper.ui.bookings.counterdialog.CounterDialog
import com.freight.shipper.ui.bookings.newload.recyclerview.NewLoadEventListener
import timber.log.Timber

class NewLoadViewModel(private val model: LoadRepository) : BaseViewModel(), NewLoadEventListener,
    CounterDialog.CounterListener {

    var isLoading = MutableLiveData<Boolean>()
    var filter: LoadFilter? = null

    val newLoads: LiveData<List<NewLoad>> get() = _newLoads
    private val _newLoads = MediatorLiveData<List<NewLoad>>()
    val newLoadsError: LiveData<String> get() = _newLoadsError
    private val _newLoadsError = MediatorLiveData<String>()

    val counterAction = ActionLiveData<NewLoad>()

    val acceptLoadResponse = ActionLiveData<String>()
    var error = ActionLiveData<String>()


    init {
        isLoading.postValue(true)
        _newLoadsError.addSource(model.newLoadError) {
            _newLoadsError.postValue(it)
        }
        _newLoads.addSource(model.newLoadList) {
            _newLoads.postValue(it)
        }
    }

    fun onFilterChange(filter: LoadFilter?) {
        this.filter = filter
        refreshNewLoad()
    }

    fun isFilterEmpty(): Boolean {
        return if (filter == null) true else {
            filter?.distance == 0 && filter?.weightFrom == 0 && filter?.weightTo == 0
        }
    }

    fun refreshNewLoad() {
        model.fetchNewLoad(filter)
    }

    // region - CounterDialog.CounterListener interface method
    override fun onSuccess() {

    }
    // endregion

    // region - NewLoadEventListener interface methods
    override fun onAcceptLoad(position: Int, load: NewLoad) {
        if (load.price.isNullOrEmpty()) {
            error.sendAction(getString(R.string.error_price_not_valid))
            return
        }
        load.loadsId?.also {
            isLoading.postValue(true)
            model.acceptLoad(it, load.price!!, object : NetworkCallback<String> {
                override fun success(result: String) {
                    acceptLoadResponse.sendAction(result)
                }

                override fun failure(errorMessage: String) {
                    error.sendAction(errorMessage)
                }
            })
        }
    }

    override fun onCounterLoad(position: Int, load: NewLoad) {
        Timber.i("$position $load")
        counterAction.sendAction(load)
    }
    // endregion
}
