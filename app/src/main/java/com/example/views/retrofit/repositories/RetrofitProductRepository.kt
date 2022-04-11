package com.example.views.retrofit.repositories

import com.example.views.RetrofitConfiguration
import com.example.views.retrofit.models.RetrofitProduct
import com.example.views.retrofit.services.ProductServices

class RetrofitProductRepository {
    private var service =
        RetrofitConfiguration.build(ProductServices::class.java) as ProductServices

    suspend fun getProduct(productID: Int): List<RetrofitProduct> {
        return service.getProduct(productID)
    }

    suspend fun getProducts(): List<RetrofitProduct> {
        return service.getProducts()
    }

    suspend fun createProduct(product: RetrofitProduct) {
        return service.createProduct(product)
    }

    suspend fun updateProduct(productID: Int, product: RetrofitProduct) {
        return service.updateProduct(productID, product)
    }

    suspend fun deleteProduct(productID: Int) {
        return service.deleteProduct(productID)
    }
}