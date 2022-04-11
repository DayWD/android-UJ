package com.example.views.retrofit.services

import retrofit2.http.GET
import retrofit2.http.Path

interface StripeServices {

    @GET("stripe/{id}")
    suspend fun getPrice(@Path("id") productID: Int): String

}