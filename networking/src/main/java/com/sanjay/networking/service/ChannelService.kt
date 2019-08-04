package com.sanjay.networking.service

import com.sanjay.networking.constraint.Filter
import com.sanjay.networking.constraint.Sort
import com.sanjay.networking.response.ApiResponse
import com.sanjay.networking.response.model.Artist
import com.sanjay.networking.response.model.Channel
import com.sanjay.networking.response.model.Gallery
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Contract for Channel-related REST API calls
 *
 * A Retrofit object will create the actual implementation of this Service
 */

interface ChannelService {
    @GET("/v0/channels")
    fun getChannels(@Query("page") page: Int? = 1,
                    @Query("count") count: Int? = Int.MAX_VALUE,
                    @Query("sort") sort: Sort? = null,
                    @Query("filter") filter: Filter? = null): Call<ApiResponse<List<Channel>>>

    @GET("/v0/channels/{channelId}")
    fun getChannel(@Path("channelId") partnerId: Long): Call<ApiResponse<Channel>>

}