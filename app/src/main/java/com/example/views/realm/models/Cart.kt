package com.example.views.realm.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Cart(
    @PrimaryKey
    var cart_id: Int = -1,
    var cart_user_id: Int = -1,
    var cart_product_id: Int = -1,
    var cart_product_quantity: Int = -1
) : RealmObject()