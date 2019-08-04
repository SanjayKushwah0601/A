package com.sanjay.networking.service

import com.sanjay.networking.response.ApiResponse
import com.sanjay.networking.response.model.Category
import com.sanjay.networking.response.model.Group
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface GroupService {
    @GET("v0/groups")
    fun getGroups(): Call<ApiResponse<List<Group>>>

    @GET("v0/groups/{groupId}")
    fun getGroup(@Path("groupId") groupId: Long): Call<ApiResponse<Group>>

    @GET("v0/groups/{groupId}/categories")
    fun getGroupCategories(@Path("groupId") groupId: Long,
                           @Query("page") page: Int? = 1,
                           @Query("count") count: Int? = Int.MAX_VALUE): Call<ApiResponse<List<Category>>>
}