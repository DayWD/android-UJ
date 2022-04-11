package com.example.views.realm.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class User(
    @PrimaryKey
    var user_id: Int = -1,
    @Required
    var login: String = "",
    @Required
    var email: String = "",
    @Required
    var password: String = ""
) : RealmObject()