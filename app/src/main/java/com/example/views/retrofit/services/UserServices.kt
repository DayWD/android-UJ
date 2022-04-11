package com.example.views.retrofit.services

import com.example.views.retrofit.models.RetrofitUser
import retrofit2.http.*

interface UserServices {

    @GET("user/{id}")
    suspend fun getUser(@Path("id") userID: Int): List<RetrofitUser>

    @GET("user")
    suspend fun getUsers(): List<RetrofitUser>

    @POST("user")
    suspend fun createUser(@Body user: RetrofitUser)

    @PUT("user/{id}")
    suspend fun updateUser(@Path("id") userID: Int, @Body user: RetrofitUser)

    @DELETE("user/{id}")
    suspend fun deleteUser(@Path("id") userID: Int)
}