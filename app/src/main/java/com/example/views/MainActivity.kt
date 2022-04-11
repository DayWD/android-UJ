package com.example.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.views.realm.repositories.RealmSynchronization
import com.stripe.android.PaymentConfiguration
import io.realm.Realm

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Realm.init(this)
        RealmConfiguration.realmConfig()

        RealmSynchronization.synchronizeAll()

        PaymentConfiguration.init(
            applicationContext,
            BuildConfig.STRIPE_PUBLIC_KEY
        )
    }
}