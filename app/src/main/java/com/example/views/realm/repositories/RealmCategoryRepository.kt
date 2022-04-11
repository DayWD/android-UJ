package com.example.views.realm.repositories

import com.example.views.realm.models.Category
import com.example.views.retrofit.repositories.RetrofitCategoryRepository
import io.realm.Realm

class RealmCategoryRepository {

    private val config = com.example.views.RealmConfiguration.realmConfig()
    private val realm = Realm.getInstance(config)

    suspend fun synchronizeCategories() {
        val categoriesList = RetrofitCategoryRepository().getCategories()
        for (i in categoriesList) {
            addToCategory(i.category_id, i.category_name)
        }
        println(categoriesList)
    }

    fun deleteCategory() {
        realm.executeTransaction { realmTr ->
            realmTr.delete(Category::class.java)
        }
    }

    fun addToCategory(category_id: Int, category_name: String) {
        val category = Category(category_id, category_name)
        print(category)
        realm.executeTransaction { realmTr ->
            realmTr.insert(category)
        }
    }

    fun removeFromCart(category_id: Int) {
        realm.executeTransaction { realmTr ->
            val category = realmTr.where(Category::class.java)
                .equalTo("category_id", category_id)
                .findFirst()
            category?.deleteFromRealm()
        }
    }

    fun getCart(category_id: Int): Category? {
        val category = realm.where(Category::class.java)
            .equalTo("category_id", category_id)
            .findFirst()!!
        return realm.copyFromRealm(category)
    }
}