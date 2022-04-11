package com.example.views.realm.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class Product(
    @PrimaryKey
    var product_id: Int = -1,
    var product_price: Int = -1,
    @Required
    var product_name: String = "",
    var product_category_id: Int = -1,
    var description: String = ""
) : RealmObject()