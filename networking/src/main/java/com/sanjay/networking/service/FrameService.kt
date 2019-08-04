package com.sanjay.networking.service

import com.sanjay.networking.annotations.DirectionString
import com.sanjay.networking.request.FrameConnectData
import com.sanjay.networking.response.frame.*
import com.sanjay.networking.response.model.Frame
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FrameService {
    @get:GET("remote/identify")
    val frame: Call<Frame>

    @get:GET("remote/get_gallery_status_json")
    val currentGallery: Call<GalleryInfoResponse>

    @get:GET("remote/control_check/sleep")
    val sleepStatus: Call<SleepStatusResponse>

    @get:GET("remote/get_backlight")
    val brightness: Call<BacklightStatusResponse>

    @get:GET("remote/get_wifi_connections_json/")
    val frameWifiConnections: Call<FrameNetworksResponse>

    @GET("remote/control_command/set_key/{direction}")
    fun swipe(@DirectionString
              @Path("direction") direction: String): Call<Any>

    @GET("remote/control_command/change_item/{id}")
    fun changeImage(@Path("id") id: Long): Call<Any>

    @GET("remote/control_command/set_key/reset")
    fun reset(): Call<Any>

    @GET("remote/control_command/resume")
    fun exitSleepMode(): Call<Any>

    @GET("remote/control_command/suspend")
    fun enterSleepMode(): Call<Any>

    @GET("remote/control_command/set_backlight/{brightness}")
    fun setBrightness(@Path("brightness") brightness: Int): Call<Any>

    @GET("remote/control_command/change_gallery/{id}")
    fun setGallery(@Path("id") galleryId: Long): Call<Any>

    @GET("remote/control_command/als_calibrate/off")
    fun calibrateBrightness(): Call<Any>

    @GET("remote/control_command/set_orientation/{orientation}")
    fun setOrientation(@Path("orientation") orientation: String): Call<Any>

    @GET("remote/control_command/delete_wifi_connection/{networkName}")
    fun deleteWifi(@Path("networkName") ssid: String): Call<DeleteWifiResponse>

    @GET("remote/identify/")
    fun identify(): Call<FrameIdentityResponse>

    @GET("remote/control_command/softap/0")
    fun disconnect(): Call<Any>

    @POST("remote/control_command_post/{connectMode}")
    fun connectFrame(@Path("connectMode") mode: String, @Body body: FrameConnectData): Call<Any>

    @GET("remote/control_command/locale/{locale}")
    fun setLocale(@Path("locale") locale: String): Call<Unit>
}