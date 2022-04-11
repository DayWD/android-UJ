package com.example.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class About : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
    }

    fun openProductsPage(view: android.view.View) {
        val intent = Intent(this, Products::class.java)
        startActivity(intent)
    }
}