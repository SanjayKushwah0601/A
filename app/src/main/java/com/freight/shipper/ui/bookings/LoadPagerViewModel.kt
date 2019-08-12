package com.freight.shipper.ui.bookings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.freight.shipper.core.platform.BaseViewModel
import com.freight.shipper.model.ActiveLoad
import com.freight.shipper.repository.LoadRepository

class LoadPagerViewModel(private val model: LoadRepository) : BaseViewModel() {

    var isLoading = MutableLiveData<Boolean>()
    var error = MediatorLiveData<String>()


    val activeLoads: LiveData<List<ActiveLoad>>
        get() {
            fetchActiveLoad()
            return _activeLoads
        }
    private val _activeLoads = MediatorLiveData<List<ActiveLoad>>()

    fun fetchActiveLoad() {
        isLoading.postValue(true)
        model.forgotPassword(Pair(_activeLoads, error))
    }
}
