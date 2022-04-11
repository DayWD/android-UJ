package com.example.views.retrofit.models

data class RetrofitOrderInfo(
    val order_id: Int = -1,
    val order_user_id: Int = -1,
    val order_name: String = "",
    val order_address: String = "",
    val order_phone: Int = -1,
    val order_mail: String = ""
)