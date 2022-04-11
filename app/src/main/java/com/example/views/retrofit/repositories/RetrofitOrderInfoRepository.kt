package com.example.views.retrofit.repositories

import com.example.views.RetrofitConfiguration
import com.example.views.retrofit.models.RetrofitOrderInfo
import com.example.views.retrofit.services.OrderInfoServices

class RetrofitOrderInfoRepository {
    private var service =
        RetrofitConfiguration.build(OrderInfoServices::class.java) as OrderInfoServices

    suspend fun getOrder(orderID: Int): List<RetrofitOrderInfo> {
        return service.getOrder(orderID)
    }

    suspend fun getOrders(): List<RetrofitOrderInfo> {
        return service.getOrders()
    }

    suspend fun createOrder(order: RetrofitOrderInfo) {
        return service.createOrder(order)
    }

    suspend fun updateOrder(orderID: Int, order: RetrofitOrderInfo) {
        return service.updateOrder(orderID, order)
    }

    suspend fun deleteOrder(orderID: Int) {
        return service.deleteOrder(orderID)
    }
}