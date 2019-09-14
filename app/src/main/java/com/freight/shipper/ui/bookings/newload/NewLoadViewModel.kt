package com.freight.shipper.ui.bookings.newload

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.freight.shipper.core.platform.ActionLiveData
import com.freight.shipper.core.platform.BaseViewModel
import com.freight.shipper.model.NewLoad
import com.freight.shipper.repository.LoadRepository
import com.freight.shipper.ui.bookings.newload.recyclerview.NewLoadEventListener
import timber.log.Timber

class NewLoadViewModel(private val model: LoadRepository) : BaseViewModel(), NewLoadEventListener {

    var isLoading = MutableLiveData<Boolean>()

    val newLoads: LiveData<List<NewLoad>> get() = _newLoads
    private val _newLoads = MediatorLiveData<List<NewLoad>>()
    val newLoadsError: LiveData<String> get() = _newLoadsError
    private val _newLoadsError = MediatorLiveData<String>()

    val counterAction = ActionLiveData<NewLoad>()

    init {
        _newLoadsError.addSource(model.newLoadError) {
            _newLoadsError.postValue(it)
        }
        _newLoads.addSource(model.newLoadList) {
            _newLoads.postValue(it)
        }
    }

    fun refreshNewLoad() {
        model.fetchNewLoad()
    }

    private val _acceptLoadResponse = MediatorLiveData<String>()
    val acceptLoadResponse: LiveData<String> get() = _acceptLoadResponse
    var error = MediatorLiveData<String>()

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
}
