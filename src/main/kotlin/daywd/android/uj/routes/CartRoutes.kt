package daywd.android.uj.routes

import daywd.android.uj.models.Cart
import daywd.android.uj.models.Product
import daywd.android.uj.tables.CartsTable
import daywd.android.uj.tables.ProductsTable
import daywd.android.uj.tables.ProductsTable.description
import daywd.android.uj.tables.ProductsTable.product_category_id
import daywd.android.uj.tables.ProductsTable.product_name
import daywd.android.uj.tables.ProductsTable.product_price
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.cartSerialization(){
    routing {
        createCart()
        searchCart()
        editCart()
        deleteCart()
    }
}

fun Route.deleteCart(){
    delete ("/cart/{id}"){
        val id: Int = call.parameters["id"]?.toInt() ?: -1
        if (id != -1) {
            transaction {
                CartsTable.deleteWhere { CartsTable.cart_id eq id }
            }
            call.respond(HttpStatusCode.OK,"Data in cart has deleted")
        }
        else call.respond(HttpStatusCode.NoContent)
    }
}

fun Route.editCart(){
    put("/cart/{id}") {
        val id: Int = call.parameters["id"]?.toInt() ?: -1
        val cart = call.receive<Cart>()
        if (id != -1) {
            transaction {
                CartsTable.update({ CartsTable.cart_id eq id }) {
                    it[cart_user_id] = cart.cart_user_id
                    it[cart_product_id] = cart.cart_product_id
                    it[cart_product_quantity] = cart.cart_product_quantity
                }
            }
            call.respond(HttpStatusCode.OK, "Cart data has modified")
        }
        else call.respond(HttpStatusCode.NoContent)
    }
}

fun Route.searchCart(){
    get ("/cart") {
        val carts: MutableList<Cart> = ArrayList()
        transaction {
            val query = CartsTable.selectAll().toList()
            query.forEach {
                carts.add(Cart(it[CartsTable.cart_id], it[CartsTable.cart_user_id], it[CartsTable.cart_product_id], it[CartsTable.cart_product_quantity]))
            }
        }
        call.respond(HttpStatusCode.OK,carts)
    }

    get ("/cart/{id}") {    // user ID
        val id: Int = call.parameters["id"]?.toInt() ?: -1
        val cart: MutableList<Cart> = ArrayList()
        if (id != -1) {
            transaction {
                val query = CartsTable.select { CartsTable.cart_user_id eq id }.toList()
                query.forEach {
                    cart.add(Cart(it[CartsTable.cart_id], it[CartsTable.cart_user_id], it[CartsTable.cart_product_id], it[CartsTable.cart_product_quantity]))
                }
            }
            call.respond(HttpStatusCode.OK,cart)
        }
        else call.respond(HttpStatusCode.NoContent)
    }
}

fun Route.createCart(){
    post("/cart") {
        val cart = call.receive<Cart>()
        transaction {
            CartsTable.insert {
                it[cart_user_id] = cart.cart_user_id
                it[cart_product_id] = cart.cart_product_id
                it[cart_product_quantity] = cart.cart_product_quantity
            }
        }
        call.respond(HttpStatusCode.Created, "Product to personal cart inserted")
    }
}