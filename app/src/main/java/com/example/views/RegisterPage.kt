package com.example.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class RegisterPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)
    }

    fun createAccount(view: android.view.View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}