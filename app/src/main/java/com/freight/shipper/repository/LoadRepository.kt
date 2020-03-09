package com.freight.shipper.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.freight.shipper.R
import com.freight.shipper.core.persistence.network.client.server.APIContract
import com.freight.shipper.core.persistence.network.date.DateConstants
import com.freight.shipper.core.persistence.network.dispatchers.DispatcherProvider
import com.freight.shipper.core.persistence.network.dispatchers.DispatcherProviderImpl
import com.freight.shipper.core.persistence.network.response.*
import com.freight.shipper.core.persistence.network.result.APIResult
import com.freight.shipper.core.persistence.network.result.NetworkCallback
import com.freight.shipper.core.persistence.preference.LoginManager
import com.freight.shipper.extensions.BaseRepository
import com.freight.shipper.model.LoadFilter
import com.freight.shipper.model.LoadStatus
import com.freight.shipper.utils.StringUtil.getString
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import timber.log.Timber
import java.io.File
import java.util.*


/**
 * @CreatedBy Sanjay Kushwah on 8/4/2019.
 * sanjaykushwah0601@gmail.com
 */
open class LoadRepository(
    private val api: APIContract,
    private val loginManager: LoginManager,
    val dispatcher: DispatcherProvider = DispatcherProviderImpl()
) : BaseRepository() {

    private val _newLoadList = MutableLiveData<List<NewLoad>>()
    val newLoadList: LiveData<List<NewLoad>> get() = _newLoadList

    private val _newLoadError = MutableLiveData<String>()
    val newLoadError: LiveData<String> get() = _newLoadError

    fun getLoadDetails(loadId: String, callback: NetworkCallback<LoadDetail>) {
        GlobalScope.launch(dispatcher.io) {
            val result = api.getLoadDetail(loadId)
            withContext(dispatcher.main) {
                when (result) {
                    is APIResult.Success -> callback.success(result.response.data)
                    is APIResult.Failure -> callback.failure(result.error?.message ?: "")
                }
            }
        }
    }

    fun uploadInvoice(loadId: String, file: File, callback: NetworkCallback<String>) {
        GlobalScope.launch(dispatcher.io) {

            val builder = MultipartBody.Builder()
            builder.setType(MultipartBody.FORM)
            builder.addFormDataPart("loads_id", loadId)

            builder.addFormDataPart(
                "signImage",
                file.name,
                RequestBody.create(MediaType.parse("multipart/form-data"), file)
            )

            val requestBody = builder.build()
            val result = api.uploadInvoice(requestBody)
            withContext(dispatcher.main) {
                when (result) {
                    is APIResult.Success -> callback.success(result.response.getMessage())
                    is APIResult.Failure -> callback.failure(result.error?.message ?: "")
                }
            }
        }
    }


    fun getMasterConfigData() {
        GlobalScope.launch(dispatcher.io) { api.getMasterConfigData() }
    }

    fun fetchNewLoad(filter: LoadFilter?) {
        GlobalScope.launch(dispatcher.io) {
            val strDate = DateConstants.getDateTimeFormat().format(Date())
            val result = api.getNewLoad(filter)
            withContext(dispatcher.main) {
                when (result) {
                    is APIResult.Success ->
                        _newLoadList.postValue(result.response.data)

                    is APIResult.Failure -> {
                        _newLoadList.postValue(listOf())
                        _newLoadError.postValue(result.error?.message ?: "")
                    }
                }
            }
        }
    }

    fun fetchActiveLoad(observer: Pair<MediatorLiveData<List<ActiveLoad>>, MediatorLiveData<String>>) {
        val (success, failure) = setupObserver(observer)
        GlobalScope.launch(dispatcher.io) {
            val result = api.getActiveLoad()
            withContext(dispatcher.main) {
                when (result) {
                    is APIResult.Success -> {
                        Timber.e("Success Sanjay: ${result.response}")
                        success.value = result.response.data
                    }
                    is APIResult.Failure -> {
                        Timber.e("Failure Sanjay: ${result.error}")
                        success.value = listOf()
                        failure.value = result.error?.message ?: ""
                    }
                }
            }
        }
    }

    fun fetchPastLoad(observer: Pair<MediatorLiveData<List<PastLoad>>, MediatorLiveData<String>>) {
        val (success, failure) = setupObserver(observer)
        GlobalScope.launch(dispatcher.io) {
            val result = api.getPastLoad()
            withContext(dispatcher.main) {
                when (result) {
                    is APIResult.Success -> {
                        Timber.e("Success Sanjay: ${result.response}")
                        success.value = result.response.data
                    }
                    is APIResult.Failure -> {
                        Timber.e("Failure Sanjay: ${result.error}")
                        success.value = listOf()
                        failure.value = result.error?.message ?: ""
                    }
                }
            }
        }
    }

    fun acceptLoad(loadId: String, price: String, callback: NetworkCallback<String>) {
        GlobalScope.launch(dispatcher.io) {
            val result = api.addLoadOfferPrice(loadId, price)
            withContext(dispatcher.main) {
                when (result) {
                    is APIResult.Success -> {
                        callback.success(getString(R.string.load_accept_success_message))
                        Timber.e("Success Sanjay: ${result.response}")
                    }
                    is APIResult.Failure -> {
                        Timber.e("Failure Sanjay: ${result.error}")
                        callback.failure(result.error?.message ?: "")
                    }
                }
            }
        }
    }

    fun updateLoadStatus(
        loadId: String, status: LoadStatus, callback: NetworkCallback<EmptyResponse>
    ) {
        GlobalScope.launch(dispatcher.io) {
            val result = api.updateLoadStatus(loadId, status)
            withContext(dispatcher.main) {
                when (result) {
                    is APIResult.Success -> callback.success(result.response.data)
                    is APIResult.Failure -> callback.failure(result.error?.message ?: "")
                }
            }
        }
    }
}