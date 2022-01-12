package daywd.android.uj.routes

import daywd.android.uj.models.OrderInfo
import daywd.android.uj.models.Product
import daywd.android.uj.models.User
import daywd.android.uj.tables.OrdersInfoTable
import daywd.android.uj.tables.OrdersInfoTable.order_address
import daywd.android.uj.tables.OrdersInfoTable.order_mail
import daywd.android.uj.tables.OrdersInfoTable.order_name
import daywd.android.uj.tables.OrdersInfoTable.order_phone
import daywd.android.uj.tables.OrdersInfoTable.order_user_id
import daywd.android.uj.tables.ProductsTable
import daywd.android.uj.tables.ProductsTable.description
import daywd.android.uj.tables.ProductsTable.product_category_id
import daywd.android.uj.tables.ProductsTable.product_id
import daywd.android.uj.tables.ProductsTable.product_name
import daywd.android.uj.tables.ProductsTable.product_price
import daywd.android.uj.tables.UsersTable
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.productSerialization(){
    routing {
        createProduct()
        searchProduct()
        editProduct()
        deleteProduct()
    }
}

fun Route.deleteProduct(){
    delete ("/product/{id}"){
        val id: Int = call.parameters["id"]?.toInt() ?: -1
        if (id != -1) {
            transaction {
                ProductsTable.deleteWhere { product_id eq id }
            }
            call.respond(HttpStatusCode.OK,"Product Deleted")
        }
        else call.respond(HttpStatusCode.NoContent)
    }
}
fun Route.editProduct(){
    put("/product/{id}") {
        val id: Int = call.parameters["id"]?.toInt() ?: -1
        val product = call.receive<Product>()
        if (id != -1) {
            transaction {
                ProductsTable.update({ product_id eq id }) {
                    it[product_price] = product.product_price
                    it[product_name] = product.product_name
                    it[product_category_id] = product.product_category_id
                    it[description] = product.description
                }
            }
            call.respond(HttpStatusCode.OK, "Product data has modified")
        }
        else call.respond(HttpStatusCode.NoContent)
    }
}
fun Route.searchProduct(){
    get ("/product") {
        val products: MutableList<Product> = ArrayList()
        transaction {
            val query = ProductsTable.selectAll().toList()
            query.forEach {
                products.add(Product(it[product_id], it[product_price], it[product_name], it[product_category_id], it[description]))
            }
        }
        call.respond(HttpStatusCode.OK,products)
    }

    get("/product/{id}") {
        val id: Int = call.parameters["id"]?.toInt() ?: -1
        var product = Product()
        if (id != -1) {
            transaction {
                val query = ProductsTable.select { product_id eq id }.toList()[0]
                    product = Product(id, query[product_price], query[product_name], query[product_category_id], query[description])
                }
                call.respond(HttpStatusCode.OK,product)
        }
        else call.respond(HttpStatusCode.NoContent)
    }
}
fun Route.createProduct() {
    post("/product") {
        val product = call.receive<Product>()
        transaction {
            ProductsTable.insert {
                it[product_price] = product.product_price
                it[product_name] = product.product_name
                it[product_category_id] = product.product_category_id
                it[description] = product.description
            }
        }
        call.respond(HttpStatusCode.Created, "Product inserted")
    }
}