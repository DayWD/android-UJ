package daywd.android.uj.routes

import daywd.android.uj.models.Category
import daywd.android.uj.tables.CategoriesTable
import daywd.android.uj.tables.CategoriesTable.category_name
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

fun Application.categorySerialization(){
    routing {
        createCategory()
        searchCategory()
        editCategory()
        deleteCategory()
    }
}

fun Route.deleteCategory(){
    delete ("/category/{id}"){
        val id: Int = call.parameters["id"]?.toInt() ?: -1
        if (id != -1) {
            transaction {
                CategoriesTable.deleteWhere { CategoriesTable.category_id eq id }
            }
            call.respond("Category Deleted")
        }
        else call.respond(HttpStatusCode.NotFound)
    }
}
fun Route.editCategory(){
    put("/category/{id}") {
        val id: Int = call.parameters["id"]?.toInt() ?: -1
        val category = call.receive<Category>()
        if (id != -1) {
            transaction {
                CategoriesTable.update({ CategoriesTable.category_id eq id }) {
                    it[category_name] = category.category_name
                }
            }
            call.respond(HttpStatusCode.OK, "Category data has modified")
        }
        else call.respond(HttpStatusCode.NotFound)
    }
}
fun Route.searchCategory(){
    get("/category/{id}") {
        val id: Int = call.parameters["id"]?.toInt() ?: -1
        var category = Category(id)
        if (id != -1){
            transaction {
                val query = CategoriesTable.select { CategoriesTable.category_id eq id }.toList()[0]
                category = Category(id, query[category_name])
            }
            call.respond(HttpStatusCode.Found, category)
        }
        else call.respond(HttpStatusCode.NotFound)
    }
}
fun Route.createCategory(){
    post("/category") {
        val category = call.receive<Category>()
        transaction {
            CategoriesTable.insert {
                it[category_name] = category.category_name
            }
        }
        call.respond(HttpStatusCode.Created,"Category inserted")
    }
}