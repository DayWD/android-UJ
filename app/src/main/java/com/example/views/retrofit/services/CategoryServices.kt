package com.example.views.retrofit.services

import com.example.views.retrofit.models.RetrofitCategory
import retrofit2.http.*

interface CategoryServices {

    @GET("category/{id}")
    suspend fun getCategory(@Path("id") categoryID: Int): List<RetrofitCategory>

    @GET("category")
    suspend fun getCategories(): List<RetrofitCategory>

    @POST("category")
    suspend fun createCategory(@Body category: RetrofitCategory)

    @PUT("category/{id}")
    suspend fun updateCategory(@Path("id") categoryID: Int, @Body category: RetrofitCategory)

    @DELETE("category/{id}")
    suspend fun deleteCategory(@Path("id") categoryID: Int)
}