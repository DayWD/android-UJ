package com.example.views.retrofit.repositories

import com.example.views.RetrofitConfiguration
import com.example.views.retrofit.models.RetrofitCategory
import com.example.views.retrofit.services.CategoryServices

class RetrofitCategoryRepository {
    private var service =
        RetrofitConfiguration.build(CategoryServices::class.java) as CategoryServices

    suspend fun getCategory(categoryID: Int): List<RetrofitCategory> {
        return service.getCategory(categoryID)
    }

    suspend fun getCategories(): List<RetrofitCategory> {
        return service.getCategories()
    }

    suspend fun createCategory(category: RetrofitCategory) {
        return service.createCategory(category)
    }

    suspend fun updateCategory(categoryID: Int, category: RetrofitCategory) {
        return service.updateCategory(categoryID, category)
    }

    suspend fun deleteCategory(categoryID: Int) {
        return service.deleteCategory(categoryID)
    }
}