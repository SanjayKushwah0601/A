package com.freight.shipper.ui.bookings.newload

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.freight.shipper.core.platform.BaseViewModel
import com.freight.shipper.model.NewLoad
import com.freight.shipper.repository.LoadRepository
import com.freight.shipper.ui.bookings.newload.recyclerview.NewLoadEventListener

class NewLoadViewModel(private val model: LoadRepository) : BaseViewModel(), NewLoadEventListener {

    var isLoading = MutableLiveData<Boolean>()

    val newLoads: LiveData<List<NewLoad>> get() = _newLoads
    private val _newLoads = MediatorLiveData<List<NewLoad>>()
    val newLoadsError: LiveData<String> get() = _newLoadsError
    private val _newLoadsError = MediatorLiveData<String>()

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
}
