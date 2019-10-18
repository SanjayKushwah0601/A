package com.freight.shipper.ui.route

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.freight.shipper.FreightApplication
import com.freight.shipper.R
import com.freight.shipper.core.persistence.network.response.ActiveLoad
import com.freight.shipper.core.platform.BaseViewModelFactory
import com.freight.shipper.databinding.ActivityRouteBinding
import com.freight.shipper.extensions.setupToolbar
import com.freight.shipper.extensions.showErrorMessage
import com.freight.shipper.model.IntentExtras
import com.freight.shipper.repository.RouteRepository
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.toolbar.*

class RouteActivity : LocationActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    // region - Private fields
    private val viewModel: RouteViewModel by lazy {
        ViewModelProviders.of(this, BaseViewModelFactory {
            RouteViewModel(
                intent.getParcelableExtra(IntentExtras.ACTIVE_LOAD) as ActiveLoad,
                RouteRepository(
                    FreightApplication.instance.api,
                    FreightApplication.instance.loginManager
                )
            )
        }).get(RouteViewModel::class.java)
    }
    private lateinit var binding: ActivityRouteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        initUI()
        setupObservers()
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun setupObservers() {
        viewModel.error.observe(this, Observer { showErrorMessage(it) })

        viewModel.successRouteResponse.observe(this, Observer {
            Log.e("SuccessRoute", it.toString())
        })
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        setupMap(viewModel.activeLoad)
    }

    private fun setupMap(load: ActiveLoad) {
        // Add a marker in Sydney and move the camera
        val pickLat = load.pickLatitude?.toDoubleOrNull()
        val pickLong = load.pickLongitude?.toDoubleOrNull()
        val sydney: LatLng = if (pickLat != null && pickLong != null)
            LatLng(pickLat, pickLong)
        else LatLng(-34.0, 151.0)

        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Pickup"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isRotateGesturesEnabled = false
        mMap.uiSettings.isMapToolbarEnabled = false
        mMap.uiSettings.isTiltGesturesEnabled = false
        mMap.isMyLocationEnabled = false

        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(
            sydney, 15.5f
        )
        mMap.animateCamera(cameraUpdate)
        fetchLastKnownLocation()
    }

    override fun onLastLocationFound(lat: Double, lng: Double) {
        viewModel.setCurrentLocation(LatLng(lat, lng))
    }

    private fun initUI() {
        setupToolbar(
            toolbar,
            enableUpButton = true,
            title = getString(R.string.arrived_at_pickup)
        )
//        setAdapter()
    }

    private fun initViewModel() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_route)
        binding.viewModel = viewModel
        binding.loadModel = intent.getParcelableExtra(IntentExtras.ACTIVE_LOAD) as ActiveLoad
//        binding.executePendingBindings()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
