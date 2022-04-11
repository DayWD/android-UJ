package com.example.views.retrofit.repositories

import com.example.views.RetrofitConfiguration
import com.example.views.retrofit.models.RetrofitCart
import com.example.views.retrofit.services.CartService

class RetrofitCartRepository {
    private var service = RetrofitConfiguration.build(CartService::class.java) as CartService

    suspend fun getCart(userID: Int): List<RetrofitCart> {
        return service.getCart(userID)
    }

    suspend fun getCarts(): List<RetrofitCart> {
        return service.getCarts()
    }

    suspend fun createCart(cart: RetrofitCart) {
        return service.createCart(cart)
    }

    suspend fun updateCart(cartId: Int, cart: RetrofitCart) {
        return service.updateCart(cartId, cart)
    }

    suspend fun deleteCart(cartId: Int) {
        return service.deleteCart(cartId)
    }
}