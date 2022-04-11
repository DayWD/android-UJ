package com.example.views.realm.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class Category(
    @PrimaryKey
    var category_id: Int = -1,
    @Required
    var category_name: String = ""
) : RealmObject()