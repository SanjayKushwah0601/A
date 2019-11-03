package com.freight.shipper.ui.route

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.freight.shipper.BuildConfig
import com.freight.shipper.R
import com.freight.shipper.core.platform.BaseActivity
import com.freight.shipper.extensions.showErrorMessage
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*


/**
 * @CreatedBy Sanjay Kushwah on 10/17/2019.
 * sanjaykushwah0601@gmail.com
 */
abstract class LocationActivity : BaseActivity() {


    private val REQUEST_PERMISSIONS_REQUEST_CODE: Int = 567
    private val TAG: String = "LocationActivity"

    /**
     * Provides the entry point to the Fused Location Provider API.
     */
    private lateinit var mFusedLocationClient: FusedLocationProviderClient

    /**
     * Represents a geographical location.
     */
    private var mLastLocation: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    abstract fun onLastLocationFound(lat: Double, lng: Double)

    fun fetchLastKnownLocation() {
        if (!checkPermissions()) {
            requestPermissions()
        } else {
            getLastLocation()
        }
    }

    fun drawRoute(mMap: GoogleMap, locationList: MutableList<LatLng>) {
        if (locationList.isEmpty()) {
            showErrorMessage(getString(R.string.no_rout_found))
            return
        }

        val lineOptions = PolylineOptions()

        lineOptions.addAll(locationList)
        lineOptions.width(5f)
        lineOptions.color(Color.BLUE)

        mMap.clear()
        mMap.addPolyline(lineOptions)
        val builder = LatLngBounds.Builder()
        if (locationList.firstOrNull() != null)
            builder.include(locationList.first())
        if (locationList.lastOrNull() != null)
            builder.include(locationList.last())
        val bounds = builder.build()

        if (locationList.firstOrNull() != null) {
            mMap.addMarker(
                MarkerOptions()
                    .position(locationList.first())
                    .draggable(true)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            )
        }

        if (locationList.lastOrNull() != null) {
            mMap.addMarker(
                MarkerOptions()
                    .position(locationList.last())
                    .draggable(true)
            )
        }

        val cu = CameraUpdateFactory.newLatLngBounds(bounds, 160)
        mMap.moveCamera(cu)
    }

    private fun checkPermissions(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(
            this@LocationActivity,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            REQUEST_PERMISSIONS_REQUEST_CODE
        )
    }

    private fun requestPermissions() {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.")

            showSnackbar(R.string.permission_rationale, android.R.string.ok,
                View.OnClickListener {
                    // Request permission
                    startLocationPermissionRequest()
                })

        } else {
            Log.i(TAG, "Requesting permission")
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startLocationPermissionRequest()
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        Log.i(TAG, "onRequestPermissionResult")
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.size <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.")
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                getLastLocation()
            } else {
                // Permission denied.

                // Notify the user via a SnackBar that they have rejected a core permission for the
                // app, which makes the Activity useless. In a real app, core permissions would
                // typically be best requested during a welcome-screen flow.

                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the user for permission (device policy or "Never ask
                // again" prompts). Therefore, a user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.
                showSnackbar(R.string.permission_denied_explanation, R.string.settings,
                    View.OnClickListener {
                        // Build intent that displays the App settings screen.
                        val intent = Intent()
                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        val uri = Uri.fromParts(
                            "package",
                            BuildConfig.APPLICATION_ID, null
                        )
                        intent.data = uri
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    })
            }
        }
    }


    /**
     * Provides a simple way of getting a device's location and is well suited for
     * applications that do not require a fine-grained location and that do not need location
     * updates. Gets the best and most recent location currently available, which may be null
     * in rare cases when a location is not available.
     *
     *
     * Note: this method should be called after location permission has been granted.
     */
    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        mFusedLocationClient.lastLocation
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful && task.result != null) {
                    mLastLocation = task.result

                    mLastLocation?.latitude?.also { latitude ->
                        mLastLocation?.longitude?.also { longitude ->
                            onLastLocationFound(latitude, longitude)
                        }
                    }

//                    mLatitudeText!!.setText(
//                        mLatitudeLabel + ":   " +
//                                (mLastLocation)!!.latitude
//                    )
//                    mLongitudeText!!.setText(
//                        mLongitudeLabel + ":   " +
//                                (mLastLocation)!!.longitude
//                    )
                } else {
                    Log.w(TAG, "getLastLocation:exception", task.exception)
                    showMessage(getString(R.string.no_location_detected))
                }
            }
    }

    /**
     * Shows a [] using `text`.

     * @param text The Snackbar text.
     */
    private fun showMessage(text: String) {
        val container = findViewById<View>(android.R.id.content)
        if (container != null) {
            Toast.makeText(this@LocationActivity, text, Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Shows a [].

     * @param mainTextStringId The id for the string resource for the Snackbar text.
     * *
     * @param actionStringId   The text of the action item.
     * *
     * @param listener         The listener associated with the Snackbar action.
     */
    private fun showSnackbar(
        mainTextStringId: Int, actionStringId: Int,
        listener: View.OnClickListener
    ) {

        Toast.makeText(this@LocationActivity, getString(mainTextStringId), Toast.LENGTH_LONG).show()
    }
}