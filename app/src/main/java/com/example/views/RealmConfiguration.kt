package com.example.views

import io.realm.RealmConfiguration

class RealmConfiguration {
    companion object {
        private val version = 1L
        private val name = "Store.realm"

        fun realmConfig(): RealmConfiguration =
            RealmConfiguration.Builder()
                .name(name)
                .schemaVersion(version)
                .allowWritesOnUiThread(true)
                .build()
    }
}