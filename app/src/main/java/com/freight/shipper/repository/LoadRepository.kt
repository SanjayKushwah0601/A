package com.freight.shipper.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.freight.shipper.core.persistence.network.client.server.APIContract
import com.freight.shipper.core.persistence.network.dispatchers.DispatcherProvider
import com.freight.shipper.core.persistence.network.dispatchers.DispatcherProviderImpl
import com.freight.shipper.core.persistence.network.result.APIResult
import com.freight.shipper.core.persistence.preference.LoginManager
import com.freight.shipper.extensions.BaseRepository
import com.freight.shipper.core.persistence.network.response.ActiveLoad
import com.freight.shipper.core.persistence.network.response.NewLoad
import com.freight.shipper.core.persistence.network.response.PastLoad
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber


/**
 * @CreatedBy Sanjay Kushwah on 8/4/2019.
 * sanjaykushwah0601@gmail.com
 */
class LoadRepository(
    private val api: APIContract,
    private val loginManager: LoginManager,
    val dispatcher: DispatcherProvider = DispatcherProviderImpl()
) : BaseRepository() {

    private val _newLoadList = MutableLiveData<List<NewLoad>>()
    val newLoadList: LiveData<List<NewLoad>> get() = _newLoadList

    private val _newLoadError = MutableLiveData<String>()
    val newLoadError: LiveData<String> get() = _newLoadError

    fun getMasterConfigData() {
        GlobalScope.launch(dispatcher.io) { api.getMasterConfigData() }
    }

    fun fetchNewLoad() {
        GlobalScope.launch(dispatcher.io) {
            val result = api.getNewLoad(null)
            withContext(dispatcher.main) {
                when (result) {
                    is APIResult.Success ->
                        _newLoadList.postValue(result.response.data)

                    is APIResult.Failure ->
                        _newLoadError.postValue(result.error?.message ?: "")
                }
            }
        }
    }

    fun fetchActiveLoad(observer: Pair<MediatorLiveData<List<ActiveLoad>>, MediatorLiveData<String>>) {
        val (success, failure) = setupObserver(observer)
        GlobalScope.launch(dispatcher.io) {
            val result = api.getLoad(null)
            withContext(dispatcher.main) {
                when (result) {
                    is APIResult.Success -> {
                        Timber.e("Success Sanjay: ${result.response}")
                        success.value = result.response.data
                    }
                    is APIResult.Failure -> {
                        Timber.e("Failure Sanjay: ${result.error}")
                        failure.value = result.error?.message ?: ""
                    }
                }
            }
        }
    }

    fun fetchPastLoad(observer: Pair<MediatorLiveData<List<PastLoad>>, MediatorLiveData<String>>) {
        val (success, failure) = setupObserver(observer)
        GlobalScope.launch(dispatcher.io) {
            withContext(dispatcher.main) {
                success.value = listOf(
                    PastLoad(
                        1,
                        "Customer1"
                    ),
                    PastLoad(2, "Customer2")
                )
            }
            /*val result = api.forgotPassword(email)
            withContext(dispatcher.main) {
                when (result) {
                    is APIResult.Success -> {
                        Timber.e("Success Sanjay: ${result.response}")
                        success.value = result.response.data
                    }
                    is APIResult.Failure -> {
                        Timber.e("Failure Sanjay: ${result.error}")
                        failure.value = result.error?.message ?: ""
                    }
                }
            }*/
        }
    }

    fun acceptLoad(
        loadId: String,
        observers: Pair<MediatorLiveData<String>, MediatorLiveData<String>>
    ) {
        val (success, failure) = setupObserver(observers)
        GlobalScope.launch(dispatcher.io) {
            val result = api.acceptLoad(loadId)
            withContext(dispatcher.main) {
                when (result) {
                    is APIResult.Success -> {
                        Timber.e("Success Sanjay: ${result.response}")
                        success.value = result.response.getMessage()
                    }
                    is APIResult.Failure -> {
                        Timber.e("Failure Sanjay: ${result.error}")
                        failure.value = result.error?.message ?: ""
                    }
                }
            }
        }
    }
}