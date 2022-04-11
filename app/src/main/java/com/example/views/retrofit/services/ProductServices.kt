package com.example.views.retrofit.services

import com.example.views.retrofit.models.RetrofitProduct
import retrofit2.http.*

interface ProductServices {

    @GET("product/{id}")
    suspend fun getProduct(@Path("id") productID: Int): List<RetrofitProduct>

    @GET("product")
    suspend fun getProducts(): List<RetrofitProduct>

    @POST("product")
    suspend fun createProduct(@Body product: RetrofitProduct)

    @PUT("product/{id}")
    suspend fun updateProduct(@Path("id") productID: Int, @Body product: RetrofitProduct)

    @DELETE("product/{id}")
    suspend fun deleteProduct(@Path("id") productID: Int)
}