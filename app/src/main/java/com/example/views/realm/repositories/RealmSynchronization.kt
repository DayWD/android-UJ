package com.example.views.realm.repositories

import io.realm.Realm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

object RealmSynchronization {

    fun synchronizeAll() {
        val config = com.example.views.RealmConfiguration.realmConfig()
        val realm = Realm.getInstance(config)

        realm.executeTransaction { realmTr ->
            realmTr.deleteAll()
        }

        runBlocking {
            withContext(Dispatchers.IO) {
                RealmCartRepository().synchronizeCarts()
                RealmCategoryRepository().synchronizeCategories()
                RealmOrderInfoRepository().synchronizeOrders()
                RealmProductRepository().synchronizeProducts()
                RealmUserRepository().synchronizeUsers()
            }
        }
    }
}