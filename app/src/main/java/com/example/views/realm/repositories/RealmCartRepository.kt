package com.example.views.realm.repositories

import com.example.views.realm.models.Cart
import com.example.views.retrofit.repositories.RetrofitCartRepository
import io.realm.Realm

class RealmCartRepository {
    private val config = com.example.views.RealmConfiguration.realmConfig()
    private val realm = Realm.getInstance(config)

    suspend fun synchronizeCart(cartID: Int) {
        val cartList = RetrofitCartRepository().getCart(cartID)
        for (i in cartList) {
            addToCarts(i.cart_id, i.cart_user_id, i.cart_product_id, i.cart_product_quantity)
        }
        println(cartList)
    }

    suspend fun synchronizeCarts() {
        val cartList = RetrofitCartRepository().getCarts()
        for (i in cartList) {
            addToCarts(i.cart_id, i.cart_user_id, i.cart_product_id, i.cart_product_quantity)
        }
        println(cartList)
    }

    fun deleteCart() {
        realm.executeTransaction { realmTr ->
            realmTr.delete(Cart::class.java)
        }
    }

    fun addToCarts(
        cart_id: Int,
        cart_user_id: Int,
        cart_product_id: Int,
        cart_product_quantity: Int
    ) {
        val cart = Cart(cart_id, cart_user_id, cart_product_id, cart_product_quantity)
        print(cart)
        realm.executeTransaction { realmTr ->
            realmTr.insert(cart)
        }
    }

    fun removeFromCart(cart_id: Int) {
        realm.executeTransaction { realmTr ->
            val cart = realmTr.where(Cart::class.java)
                .equalTo("cart_id", cart_id)
                .findFirst()
            cart?.deleteFromRealm()
        }
    }

    fun getCart(cart_user_id: Int): Cart? {
        val cart = realm.where(Cart::class.java)
            .equalTo("cart_user_id", cart_user_id)
            .findFirst()!!
        return realm.copyFromRealm(cart)
    }
}