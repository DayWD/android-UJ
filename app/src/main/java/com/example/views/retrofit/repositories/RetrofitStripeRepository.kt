package com.example.views.retrofit.repositories

import com.example.views.RetrofitConfiguration
import com.example.views.retrofit.services.StripeServices

class RetrofitStripeRepository {
    private var service = RetrofitConfiguration.build(StripeServices::class.java) as StripeServices

    suspend fun getPrice(productID: Int): String {
        return service.getPrice(productID)
    }

}