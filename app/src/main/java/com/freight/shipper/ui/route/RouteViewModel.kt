package com.freight.shipper.ui.route

import com.freight.shipper.core.persistence.network.response.ActiveLoad
import com.freight.shipper.core.platform.ActionLiveData
import com.freight.shipper.core.platform.BaseViewModel
import com.freight.shipper.repository.RouteRepository
import com.google.android.gms.maps.model.LatLng


/**
 * @CreatedBy Sanjay Kushwah on 10/8/2019.
 * sanjaykushwah0601@gmail.com
 */
class RouteViewModel(val activeLoad: ActiveLoad, val model: RouteRepository) : BaseViewModel() {

    val error = ActionLiveData<String>()
    val successRouteResponse = ActionLiveData<MutableList<LatLng>>()

    fun setCurrentLocation(latLng: LatLng) {

        val pickLat = activeLoad.pickLatitude?.toDoubleOrNull()
        val pickLong = activeLoad.pickLongitude?.toDoubleOrNull()
        val pickup: LatLng = if (pickLat != null && pickLong != null)
            LatLng(pickLat, pickLong)
        else LatLng(-34.0, 151.0)

        val destLat = activeLoad.pickLatitude?.toDoubleOrNull()
        val destLong = activeLoad.pickLongitude?.toDoubleOrNull()
        val desttination: LatLng = if (pickLat != null && pickLong != null)
            LatLng(pickLat, pickLong)
        else LatLng(-34.0, 151.0)

        getPickupRoute(latLng, pickup)
    }

    fun getPickupRoute(current: LatLng, pickup: LatLng) {
        model.getRoute(current, pickup, { handleSuccess(it) }) {
            error.sendAction(it)
        }
    }

    fun getDestinationRoute(pickup: LatLng, destination: LatLng) {
        model.getRoute(pickup, destination, { handleSuccess(it) }) {
            error.sendAction(it)
        }
    }

    fun handleSuccess(points: List<MutableList<LatLng>>) {
        successRouteResponse.sendAction(points.firstOrNull() ?: mutableListOf())
    }
}