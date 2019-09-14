package com.freight.shipper.ui.bookings.assigned

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.freight.shipper.core.platform.BaseViewModel
import com.freight.shipper.model.ActiveLoad
import com.freight.shipper.model.PastLoad
import com.freight.shipper.repository.LoadRepository

class LoadPagerViewModel(private val model: LoadRepository) : BaseViewModel() {

    var isLoading = MutableLiveData<Boolean>()
    var error = MediatorLiveData<String>()


    val activeLoads: LiveData<List<ActiveLoad>>
        get() {
            fetchActiveLoad()
            return _activeLoads
        }

    val pastLoads: LiveData<List<PastLoad>>
        get() {
            fetchPastLoad()
            return _pastLoads
        }
    private val _activeLoads = MediatorLiveData<List<ActiveLoad>>()
    private val _pastLoads = MediatorLiveData<List<PastLoad>>()

    private fun fetchActiveLoad() {
        isLoading.postValue(true)
        model.fetchActiveLoad(Pair(_activeLoads, error))
    }

    private fun fetchPastLoad() {
        isLoading.postValue(true)
        model.fetchPastLoad(Pair(_pastLoads, error))
    }
}
