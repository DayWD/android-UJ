package com.example.views.retrofit.services

import com.example.views.retrofit.models.RetrofitOrderInfo
import retrofit2.http.*

interface OrderInfoServices {

    @GET("order/{id}")
    suspend fun getOrder(@Path("id") orderID: Int): List<RetrofitOrderInfo>

    @GET("order")
    suspend fun getOrders(): List<RetrofitOrderInfo>

    @POST("order")
    suspend fun createOrder(@Body order: RetrofitOrderInfo)

    @PUT("order/{id}")
    suspend fun updateOrder(@Path("id") orderID: Int, @Body order: RetrofitOrderInfo)

    @DELETE("order/{id}")
    suspend fun deleteOrder(@Path("id") orderID: Int)
}