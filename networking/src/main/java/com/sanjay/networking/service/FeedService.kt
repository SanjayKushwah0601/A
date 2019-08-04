package com.sanjay.networking.service

import com.sanjay.networking.response.ApiResponse
import com.sanjay.networking.response.model.FeedItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FeedService {
    @GET("v0/feed")
    fun getFeed(@Query("page") page: Int = 1, @Query("count") count: Int? = Int.MAX_VALUE): Call<ApiResponse<List<FeedItem>>>
}