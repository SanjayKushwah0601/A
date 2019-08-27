package com.freight.shipper.repository

import androidx.lifecycle.MediatorLiveData
import com.freight.shipper.core.persistence.network.client.server.APIContract
import com.freight.shipper.core.persistence.network.dispatchers.DispatcherProvider
import com.freight.shipper.core.persistence.network.dispatchers.DispatcherProviderImpl
import com.freight.shipper.core.persistence.network.result.APIResult
import com.freight.shipper.core.persistence.preference.LoginManager
import com.freight.shipper.extensions.BaseRepository
import com.freight.shipper.model.ActiveLoad
import com.freight.shipper.model.PastLoad
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

    fun getMasterConfigData() {
        GlobalScope.launch(dispatcher.io) { api.getMasterConfigData() }
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
                success.value = listOf(PastLoad(1, "Customer1"), PastLoad(2, "Customer2"))
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
}