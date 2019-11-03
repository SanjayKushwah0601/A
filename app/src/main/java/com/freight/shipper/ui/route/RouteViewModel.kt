package com.freight.shipper.ui.route

import android.util.Log
import androidx.databinding.ObservableField
import com.freight.shipper.core.persistence.network.response.ActiveLoad
import com.freight.shipper.core.persistence.network.response.EmptyResponse
import com.freight.shipper.core.persistence.network.result.NetworkCallback
import com.freight.shipper.core.platform.ActionLiveData
import com.freight.shipper.core.platform.BaseViewModel
import com.freight.shipper.model.LoadStatus
import com.freight.shipper.repository.RouteRepository
import com.google.android.gms.maps.model.LatLng


/**
 * @CreatedBy Sanjay Kushwah on 10/8/2019.
 * sanjaykushwah0601@gmail.com
 */
class RouteViewModel(val activeLoad: ActiveLoad, val model: RouteRepository) : BaseViewModel() {

    var isLoading = ObservableField<Boolean>()
    val arrivedAtPickupAction = ActionLiveData<ActiveLoad>()
    val arrivedAtDestinationAction = ActionLiveData<ActiveLoad>()
    val error = ActionLiveData<String>()
    val successRouteResponse = ActionLiveData<MutableList<LatLng>>()

    fun setCurrentLocation(latLng: LatLng) {

        val pickLat = activeLoad.pickLatitude?.toDoubleOrNull()
        val pickLong = activeLoad.pickLongitude?.toDoubleOrNull()
        val pickup: LatLng = if (pickLat != null && pickLong != null)
            LatLng(pickLat, pickLong)
        else LatLng(-34.0, 151.0)

        getPickupRoute(latLng, pickup)
    }

    fun getPickupRoute(current: LatLng, pickup: LatLng) {
        model.getRoute(current, pickup, { handleSuccess(it) }) {
            error.sendAction(it)
        }
    }

    fun getDestinationRoute() {
        val pickLat = activeLoad.pickLatitude?.toDoubleOrNull()
        val pickLong = activeLoad.pickLongitude?.toDoubleOrNull()
        val pickup: LatLng = if (pickLat != null && pickLong != null)
            LatLng(pickLat, pickLong)
        else LatLng(-34.0, 151.0)

        val destLat = activeLoad.destLatitude?.toDoubleOrNull()
        val destLong = activeLoad.destLongitude?.toDoubleOrNull()
        val destination: LatLng = if (destLat != null && destLong != null)
            LatLng(destLat, destLong)
        else LatLng(-34.0, 151.0)

        model.getRoute(pickup, destination, { handleSuccess(it) }) {
            error.sendAction(it)
        }
    }

    fun onArrivedPickupClicked(loadStatus: LoadStatus) {
        updateLoadStatus(loadStatus) { arrivedAtPickupAction.sendAction(activeLoad) }
    }

    fun onArrivedDestinationClicked(loadStatus: LoadStatus) {
        updateLoadStatus(loadStatus) { arrivedAtDestinationAction.sendAction(activeLoad) }
    }

    private fun updateLoadStatus(loadStatus: LoadStatus, success: (EmptyResponse) -> Unit) {
        Log.e("UpdatedLoadStatus", loadStatus.name)
        activeLoad.loadsId?.also {
            isLoading.set(true)
            model.updateLoadStatus(
                it, loadStatus, object : NetworkCallback<EmptyResponse> {
                    override fun success(result: EmptyResponse) {
                        // TODO : Save status
                        activeLoad.loadStatusId = loadStatus.id.toString()
                        success(result)
                    }

                    override fun failure(errorMessage: String) {
                        error.postValue(errorMessage)
                    }
                })
        }
    }

    private fun handleSuccess(points: List<MutableList<LatLng>>) {
        successRouteResponse.sendAction(points.firstOrNull() ?: mutableListOf())
    }
}