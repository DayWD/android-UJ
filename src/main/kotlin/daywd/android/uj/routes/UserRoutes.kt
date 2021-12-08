package daywd.android.uj.routes

import daywd.android.uj.models.*
import daywd.android.uj.tables.UsersTable
import daywd.android.uj.tables.UsersTable.user_email
import daywd.android.uj.tables.UsersTable.user_id
import daywd.android.uj.tables.UsersTable.user_login
import daywd.android.uj.tables.UsersTable.user_password
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.userSerialization(){
    routing {
        createUser()
        searchUser()
        editUser()
        deleteUser()
    }
}

fun Route.deleteUser(){
    delete ("/user/{id}"){
        val id: Int = call.parameters["id"]?.toInt() ?: -1
        if (id != -1) {
            transaction {
                UsersTable.deleteWhere { user_id eq id }
            }
            call.respond("User Deleted")
        }
        else call.respond(HttpStatusCode.NotFound)
    }
}
fun Route.editUser(){
    put("/user/{id}") {
        val id: Int = call.parameters["id"]?.toInt() ?: -1
        val user = call.receive<User>()
        if (id != -1) {
            transaction {
                UsersTable.update({ user_id eq id }) {
                    it[user_login] = user.login
                    it[user_email] = user.email
                    it[user_password] = user.password
                }
            }
            call.respond(HttpStatusCode.OK, "User data has modified")
        }
        else call.respond(HttpStatusCode.NotFound)
    }
}
fun Route.searchUser(){
    get("/user/{id}") {
        val id: Int = call.parameters["id"]?.toInt() ?: -1
        var user = User(id)
        if (id != -1) {
            transaction {
                val query = UsersTable.select { user_id eq id }.toList()[0]
                user = User(id, query[user_login], query[user_email], query[user_password])
            }
            call.respond(HttpStatusCode.Found,user)
        }
        else call.respond(HttpStatusCode.NotFound)
    }
}
fun Route.createUser(){
    post("/user") {
        val user = call.receive<User>()
        transaction {
            UsersTable.insert {
                it[user_login] = user.login
                it[user_email] = user.email
                it[user_password] = user.password
            }
        }
        call.respond(HttpStatusCode.Created,"User inserted")
    }
}