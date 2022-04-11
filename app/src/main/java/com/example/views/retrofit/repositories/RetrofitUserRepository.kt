package com.example.views.retrofit.repositories

import com.example.views.RetrofitConfiguration
import com.example.views.retrofit.models.RetrofitUser
import com.example.views.retrofit.services.UserServices

class RetrofitUserRepository {
    private var service = RetrofitConfiguration.build(UserServices::class.java) as UserServices

    suspend fun getUser(userID: Int): List<RetrofitUser> {
        return service.getUser(userID)
    }

    suspend fun getUsers(): List<RetrofitUser> {
        return service.getUsers()
    }

    suspend fun createUser(user: RetrofitUser) {
        return service.createUser(user)
    }

    suspend fun updateUser(userID: Int, user: RetrofitUser) {
        return service.updateUser(userID, user)
    }

    suspend fun deleteUser(userID: Int) {
        return service.deleteUser(userID)
    }
}