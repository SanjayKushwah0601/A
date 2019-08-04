package com.sanjay.networking.service

import com.sanjay.networking.response.ApiResponse
import com.sanjay.networking.response.model.SearchResults
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("v0/search")
    fun search(@Query("q") searchTerm: String,
               @Query("page") page: Int? = 1,
               @Query("count") count: Int? = Int.MAX_VALUE): Call<ApiResponse<SearchResults>>
}