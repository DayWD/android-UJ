package com.example.views.retrofit.models

data class RetrofitProduct(
    val product_id: Int = -1,
    val product_price: Int = -1,
    val product_name: String = "",
    val product_category_id: Int = -1,
    val description: String = ""
)