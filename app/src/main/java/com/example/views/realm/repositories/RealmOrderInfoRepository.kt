package com.example.views.realm.repositories

import com.example.views.realm.models.OrderInfo
import com.example.views.retrofit.repositories.RetrofitOrderInfoRepository
import io.realm.Realm

class RealmOrderInfoRepository {
    private val config = com.example.views.RealmConfiguration.realmConfig()
    private val realm = Realm.getInstance(config)

    suspend fun synchronizeOrderInfo(order_id: Int) {
        val orderList = RetrofitOrderInfoRepository().getOrder(order_id)
        for (i in orderList) {
            addToOrder(
                i.order_id,
                i.order_user_id,
                i.order_name,
                i.order_address,
                i.order_phone,
                i.order_mail
            )
        }
        println(orderList)
    }

    suspend fun synchronizeOrders() {
        val cartList = RetrofitOrderInfoRepository().getOrders()
        for (i in cartList) {
            addToOrder(
                i.order_id,
                i.order_user_id,
                i.order_name,
                i.order_address,
                i.order_phone,
                i.order_mail
            )
        }
        println(cartList)
    }

    fun deleteCart() {
        realm.executeTransaction { realmTr ->
            realmTr.delete(OrderInfo::class.java)
        }
    }

    fun addToOrder(
        order_id: Int,
        order_user_id: Int,
        order_name: String,
        order_address: String,
        order_phone: Int,
        order_mail: String
    ) {
        val order =
            OrderInfo(order_id, order_user_id, order_name, order_address, order_phone, order_mail)
        print(order)
        realm.executeTransaction { realmTr ->
            realmTr.insert(order)
        }
    }

    fun removeFromOrder(order_id: Int) {
        realm.executeTransaction { realmTr ->
            val order = realmTr.where(OrderInfo::class.java)
                .equalTo("order_id", order_id)
                .findFirst()
            order?.deleteFromRealm()
        }
    }

    fun getOrder(order_id: Int): OrderInfo? {
        val order = realm.where(OrderInfo::class.java)
            .equalTo("order_id", order_id)
            .findFirst()!!
        return realm.copyFromRealm(order)
    }

    fun getOrders(): List<OrderInfo> {
        val result = realm.where(OrderInfo::class.java).findAll()
        return realm.copyFromRealm(result)
    }

    fun getHighestOrderId(): Int {
        val orders: List<OrderInfo> = getOrders()
        var highestUserID = 0
        for (order in orders) {
            if (order.order_id > highestUserID)
                highestUserID = order.order_id
        }
        return highestUserID
    }
}