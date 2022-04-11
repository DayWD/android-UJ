package com.example.views.realm.repositories

import com.example.views.realm.models.Product
import com.example.views.retrofit.repositories.RetrofitProductRepository
import io.realm.Realm

class RealmProductRepository {
    private val config = com.example.views.RealmConfiguration.realmConfig()
    private val realm = Realm.getInstance(config)

    suspend fun synchronizeProduct(product_id: Int) {
        val productList = RetrofitProductRepository().getProduct(product_id)
        for (i in productList) {
            addToProduct(
                i.product_id,
                i.product_price,
                i.product_name,
                i.product_category_id,
                i.description
            )
        }
        println(productList)
    }

    suspend fun synchronizeProducts() {
        val productList = RetrofitProductRepository().getProducts()
        for (i in productList) {
            addToProduct(
                i.product_id,
                i.product_price,
                i.product_name,
                i.product_category_id,
                i.description
            )
        }
        println(productList)
    }

    fun deleteProduct() {
        realm.executeTransaction { realmTr ->
            realmTr.delete(Product::class.java)
        }
    }

    fun addToProduct(
        product_id: Int,
        product_price: Int,
        product_name: String,
        product_category_id: Int,
        description: String
    ) {
        val product =
            Product(product_id, product_price, product_name, product_category_id, description)
        print(product)
        realm.executeTransaction { realmTr ->
            realmTr.insert(product)
        }
    }

    fun removeFromProduct(product_id: Int) {
        realm.executeTransaction { realmTr ->
            val product = realmTr.where(Product::class.java)
                .equalTo("product_id", product_id)
                .findFirst()
            product?.deleteFromRealm()
        }
    }

    fun getProduct(product_id: Int): Product? {
        val product = realm.where(Product::class.java)
            .equalTo("product_id", product_id)
            .findFirst()!!
        return realm.copyFromRealm(product)
    }
}