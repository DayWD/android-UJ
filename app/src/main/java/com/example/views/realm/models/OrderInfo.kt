package com.example.views.realm.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class OrderInfo(
    @PrimaryKey
    var order_id: Int = -1,
    var order_user_id: Int = -1,
    @Required
    var order_name: String = "",
    @Required
    var order_address: String = "",
    var order_phone: Int = -1,
    @Required
    var order_mail: String = ""
) : RealmObject()