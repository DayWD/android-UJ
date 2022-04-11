package com.example.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class Products : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)
    }

    fun openAboutMePage(view: android.view.View) {
        val intent = Intent(this, About::class.java)
        startActivity(intent)
    }

    fun addProductToCart(view: android.view.View) {
        val intent = Intent(this, Cart::class.java)
        //  Log.d("myTag", button.autofillHints.toString())
        //  intent.putExtra(view.labelFor.toString())
        startActivity(intent)
    }
}