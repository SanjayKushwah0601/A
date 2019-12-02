package com.freight.shipper.ui.bookings.newload

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.freight.shipper.core.persistence.network.response.NewLoad
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

    private val acceptObserver = Observer<String> { acceptLoadResponse.sendAction(it) }
    private val _acceptLoadResponse = MediatorLiveData<String>().apply {
        observeForever(acceptObserver)
    }
    val acceptLoadResponse = ActionLiveData<String>()

    var error = MediatorLiveData<String>()


    init {
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

    fun refreshNewLoad() {
        model.fetchNewLoad(filter)
    }

    // region - CounterDialog.CounterListener interface method
    override fun onSuccess() {

    }
    // endregion

    // region - NewLoadEventListener interface methods
    override fun onAcceptLoad(position: Int, load: NewLoad) {
        load.loadsId?.also {
            isLoading.postValue(true)
            model.acceptLoad(it, Pair(_acceptLoadResponse, error))
        }
//        acceptAction.sendAction(load)
    }

    override fun onCounterLoad(position: Int, load: NewLoad) {
        Timber.i("$position $load")
        counterAction.sendAction(load)
    }
    // endregion

    override fun onCleared() {
        super.onCleared()
        _acceptLoadResponse.removeObserver(acceptObserver)
    }
}
