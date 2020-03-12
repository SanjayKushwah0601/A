package com.freight.shipper.ui.viewinvoice

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.freight.shipper.core.persistence.network.response.LoadDetail
import com.freight.shipper.core.persistence.network.response.PastLoad
import com.freight.shipper.core.persistence.network.result.NetworkCallback
import com.freight.shipper.core.platform.ActionLiveData
import com.freight.shipper.core.platform.BaseViewModel
import com.freight.shipper.repository.LoadRepository
import timber.log.Timber


/**
 * @CreatedBy Sanjay Kushwah on 10/8/2019.
 * sanjaykushwah0601@gmail.com
 */
class ViewInvoiceViewModel(val activeLoad: PastLoad, val model: LoadRepository) : BaseViewModel() {

    // region - Private properties
    var isLoading = ObservableField<Boolean>()
    var submitInvoice = ActionLiveData<Pair<String, String>>()
    var submitInvoiceResponse = ActionLiveData<String>()
    var loadField = ObservableField<LoadDetail>()
    val loadDetail: LiveData<LoadDetail> get() = _loadDetail
    private val _loadDetail = MutableLiveData<LoadDetail>()
    val error = ActionLiveData<String>()
    // endregion

    init {
        getLoadDetail()
    }

    fun submitInvoice() {
        Timber.d("submitInvoice")
        activeLoad.loadsId?.also {
            submitInvoice.sendAction(Pair("Invoice_${it}.png", it))
        }
    }

    private fun getLoadDetail() {
        activeLoad.loadsId?.also {
            isLoading.set(true)
            model.getLoadDetails(it, object : NetworkCallback<LoadDetail> {
                override fun success(result: LoadDetail) {
                    isLoading.set(false)
                    loadField.set(result)
                    _loadDetail.postValue(result)
                }

                override fun failure(errorMessage: String) {
                    isLoading.set(false)
                    error.postValue(errorMessage)
                }
            })
        }
    }
}