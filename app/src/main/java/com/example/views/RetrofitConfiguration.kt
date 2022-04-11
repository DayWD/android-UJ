package com.example.views

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConfiguration {
    companion object {
        fun build(service: Class<*>): Any? {
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.NGROK_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(service)
        }
    }
}