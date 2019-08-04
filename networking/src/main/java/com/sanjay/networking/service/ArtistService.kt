package com.sanjay.networking.service

import com.sanjay.networking.constraint.Filter
import com.sanjay.networking.constraint.Sort
import com.sanjay.networking.response.ApiResponse
import com.sanjay.networking.response.model.Artist
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Contract for Artist-related API calls.
 *
 * A Retrofit object will create the actual implementation of this Service
 */

interface ArtistService {
    @GET("/v0/artists")
    fun getArtists(@Query("page") page: Int? = 1,
                   @Query("count") count: Int? = Int.MAX_VALUE,
                   @Query("sort") sort: Sort?,
                   @Query("filter") filter: Filter?): Call<ApiResponse<List<Artist>>>

    @GET("/v0/artists/{artistId}")
    fun getArtist(@Path("artistId") artistId: Long): Call<ApiResponse<Artist>>

    @GET("v0/{model}/{id}/artists")
    fun getModelArtists(@Path("model") model: String,
                        @Path("id") id: Long,
                        @Query("page") page: Int? = 1,
                        @Query("count") count: Int? = Int.MAX_VALUE,
                        @Query("sort") sort: Sort?,
                        @Query("filter") filter: Filter?): Call<ApiResponse<List<Artist>>>
}