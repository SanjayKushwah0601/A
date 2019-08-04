package com.sanjay.networking.service

import com.sanjay.networking.constraint.Filter
import com.sanjay.networking.constraint.Sort
import com.sanjay.networking.response.ApiResponse
import com.sanjay.networking.response.model.Article
import com.sanjay.networking.response.model.Artist
import com.sanjay.networking.response.model.Gallery
import com.sanjay.networking.response.model.Image
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Contract for Category-related REST API calls
 *
 * A Retrofit object will create the actual implementation of this Service
 */

interface ArticleService {

    @GET("/v0/articles/{id}")
    fun getArticle(@Path("id") categoryId: Long): Call<ApiResponse<Article>>

    @GET("/v0/articles/{id}/items/")
    fun getArticleImages(@Path("id") categoryId: Long,
                         @Query("page") page: Int? = 1,
                         @Query("count") count: Int? = Int.MAX_VALUE,
                         @Query("sort") sort: Sort?,
                         @Query("filter") filter: Filter?): Call<ApiResponse<List<Image>>>

    @GET("/v0/articles/{id}/galleries/")
    fun getArticleGalleries(@Path("id") categoryId: Long,
                            @Query("page") page: Int? = 1,
                            @Query("count") count: Int? = Int.MAX_VALUE,
                            @Query("sort") sort: Sort?,
                            @Query("filter") filter: Filter?): Call<ApiResponse<List<Gallery>>>

    @GET("/v0/articles/{id}/artists/")
    fun getArticleArtists(@Path("id") categoryId: Long,
                          @Query("page") page: Int? = 1,
                          @Query("count") count: Int? = Int.MAX_VALUE,
                          @Query("sort") sort: Sort?,
                          @Query("filter") filter: Filter?): Call<ApiResponse<List<Artist>>>

    @GET("v0/{model}/{id}/articles")
    fun getModelArticles(@Path("model") model: String,
                         @Path("id") id: Long,
                         @Query("page") page: Int? = 1,
                         @Query("count") count: Int? = Int.MAX_VALUE,
                         @Query("sort") sort: Sort?,
                         @Query("filter") filter: Filter?): Call<ApiResponse<List<Article>>>
}