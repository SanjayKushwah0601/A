package com.freight.shipper.repository

import android.util.Log
import com.freight.shipper.R
import com.freight.shipper.core.persistence.network.client.server.APIContract
import com.freight.shipper.core.persistence.network.dispatchers.DispatcherProvider
import com.freight.shipper.core.persistence.network.dispatchers.DispatcherProviderImpl
import com.freight.shipper.core.persistence.preference.LoginManager
import com.freight.shipper.utils.DirectionsJSONParser
import com.freight.shipper.utils.StringUtil
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


/**
 * @CreatedBy Sanjay Kushwah on 10/8/2019.
 * sanjaykushwah0601@gmail.com
 */
class RouteRepository(
    private val api: APIContract,
    private val loginManager: LoginManager,
    val dispatcher: DispatcherProvider = DispatcherProviderImpl()
) {

    @Throws(IOException::class)
    private fun downloadUrl(strUrl: String): String {
        var data = ""
        var iStream: InputStream? = null
        var urlConnection: HttpURLConnection? = null
        try {
            val url = URL(strUrl)
            // Creating an http connection to communicate with url
            urlConnection = url.openConnection() as HttpURLConnection
            // Connecting to url
            urlConnection.connect()
            // Reading data from url
            iStream = urlConnection.inputStream
            val br = BufferedReader(InputStreamReader(iStream!!))
            val sb = StringBuffer()
            var line = br.readLine()
            while (line != null) {
                sb.append(line)
                line = br.readLine()
            }
            data = sb.toString()
            br.close()

        } catch (e: Exception) {
            Log.d("Exception ", "while downloading url $e")
        } finally {
            iStream!!.close()
            urlConnection!!.disconnect()
        }
        return data
    }

    fun getRoute(
        origin: LatLng, destination: LatLng,
        success: (List<MutableList<LatLng>>) -> Unit, failure: (String) -> Unit
    ) {
        val strOrigin: String = "origin=" + origin.latitude + "," + origin.longitude
        val strDestination: String =
            "destination=" + destination.latitude + "," + destination.longitude

        val parameters = "$strOrigin&$strDestination"
        val output = "json"
        val url =
            "https://maps.googleapis.com/maps/api/directions/$output?$parameters" +
                    "&key=${StringUtil.getString(R.string.google_maps_key)}"
        GlobalScope.launch(dispatcher.io) {
            try {
                val strJson = downloadUrl(url)

                val resultJson = JSONObject(strJson)

                if (resultJson.has("error_message")) {
                    withContext(dispatcher.main) {
                        failure(resultJson.getString("error_message"))
                    }
                    return@launch
                }

                val result = DirectionsJSONParser().parse(resultJson)

                val pointsList: MutableList<MutableList<LatLng>> = mutableListOf()
                // Traversing through all the routes
                result.forEachIndexed { index, list ->
                    val points: MutableList<LatLng> = mutableListOf()
                    // Fetching i-th route
                    val path = list

                    // Fetching all the points in i-th route
                    for (j in path.indices) {
                        val point = path[j]

                        val lat = java.lang.Double.parseDouble(point["lat"]!!)
                        val lng = java.lang.Double.parseDouble(point["lng"]!!)
                        val position = LatLng(lat, lng)

                        points.add(position)
                    }
                    pointsList.add(points)

                }
                withContext(dispatcher.main) { success(pointsList) }
            } catch (e: Exception) {
                withContext(dispatcher.main) { failure(e.toString()) }
            }
        }
    }
}