package daywd.android.uj.routes

import daywd.android.uj.models.Category
import daywd.android.uj.models.Product
import daywd.android.uj.tables.CategoriesTable
import daywd.android.uj.tables.CategoriesTable.category_id
import daywd.android.uj.tables.CategoriesTable.category_name
import daywd.android.uj.tables.ProductsTable
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

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
            call.respond(HttpStatusCode.OK,"Category Deleted")
        }
        else call.respond(HttpStatusCode.NoContent)
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
        else call.respond(HttpStatusCode.NoContent)
    }
}
fun Route.searchCategory(){
    get ("/category") {
        val categories: MutableList<Category> = ArrayList()
        transaction {
            val query = CategoriesTable.selectAll().toList()
            query.forEach {
                categories.add(Category(it[category_id], it[category_name]))
            }
        }
        call.respond(HttpStatusCode.OK,categories)
    }

    get("/category/{id}") {
        val id: Int = call.parameters["id"]?.toInt() ?: -1
        var category = Category(id)
        if (id != -1){
            transaction {
                val query = CategoriesTable.select { CategoriesTable.category_id eq id }.toList()[0]
                category = Category(id, query[category_name])
            }
            call.respond(HttpStatusCode.OK, category)
        }
        else call.respond(HttpStatusCode.NoContent)
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