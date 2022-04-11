package com.example.views.realm.repositories

import com.example.views.realm.models.User
import com.example.views.retrofit.repositories.RetrofitUserRepository
import io.realm.Realm

class RealmUserRepository {
    private val config = com.example.views.RealmConfiguration.realmConfig()
    private val realm = Realm.getInstance(config)

    suspend fun synchronizeUser(user_id: Int) {
        val userList = RetrofitUserRepository().getUser(user_id)
        for (i in userList) {
            addToUser(i.user_id, i.login, i.email, i.password)
        }
        println(userList)
    }

    suspend fun synchronizeUsers() {
        val userList = RetrofitUserRepository().getUsers()
        for (i in userList) {
            addToUser(i.user_id, i.login, i.email, i.password)
        }
        println(userList)
    }

    fun deleteUser() {
        realm.executeTransaction { realmTr ->
            realmTr.delete(User::class.java)
        }
    }

    fun addToUser(user_id: Int, login: String, email: String, password: String) {
        val user = User(user_id, login, email, password)
        print(user)
        realm.executeTransaction { realmTr ->
            realmTr.insert(user)
        }
    }

    fun removeFromUser(user_id: Int) {
        realm.executeTransaction { realmTr ->
            val user = realmTr.where(User::class.java)
                .equalTo("user_id", user_id)
                .findFirst()
            user?.deleteFromRealm()
        }
    }

    fun getUser(user_id: Int): User? {
        val user = realm.where(User::class.java)
            .equalTo("user_id", user_id)
            .findFirst()!!
        return realm.copyFromRealm(user)
    }

    fun getUsers(): List<User> {
        val result = realm.where(User::class.java).findAll()
        return realm.copyFromRealm(result)
    }

    fun getHighestUserId(): Int {
        val users: List<User> = getUsers()
        var highestUserID = 0
        for (user in users) {
            if (user.user_id > highestUserID)
                highestUserID = user.user_id
        }
        return highestUserID
    }
}