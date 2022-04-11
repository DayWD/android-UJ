package com.example.views.retrofit.services

import com.example.views.retrofit.models.RetrofitCart
import retrofit2.http.*

interface CartService {

    @GET("cart/{id}")
    suspend fun getCart(@Path("id") userID: Int): List<RetrofitCart>

    @GET("cart")
    suspend fun getCarts(): List<RetrofitCart>

    @POST("cart")
    suspend fun createCart(@Body cart: RetrofitCart)

    @PUT("cart/{id}")
    suspend fun updateCart(@Path("id") cartId: Int, @Body cart: RetrofitCart)

    @DELETE("cart/{id}")
    suspend fun deleteCart(@Path("id") cartId: Int)
}