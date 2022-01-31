package daywd.android.uj.routes

import daywd.android.uj.models.OrderInfo
import daywd.android.uj.tables.OrdersInfoTable
import daywd.android.uj.tables.OrdersInfoTable.order_address
import daywd.android.uj.tables.OrdersInfoTable.order_id
import daywd.android.uj.tables.OrdersInfoTable.order_mail
import daywd.android.uj.tables.OrdersInfoTable.order_name
import daywd.android.uj.tables.OrdersInfoTable.order_phone
import daywd.android.uj.tables.OrdersInfoTable.order_user_id
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.orderInfoSerialization() {
    routing {
        createOrder()
        searchOrder()
        editOrder()
        deleteOrder()
    }
}

fun Route.deleteOrder() {
    delete("/order/{id}") {
        val id: Int = call.parameters["id"]?.toInt() ?: -1
        if (id != -1) {
            transaction {
                OrdersInfoTable.deleteWhere { OrdersInfoTable.order_id eq id }
            }
            call.respond(HttpStatusCode.OK, "Order Deleted")
        } else call.respond(HttpStatusCode.NoContent)
    }
}

fun Route.editOrder() {
    put("/order/{id}") {
        val id: Int = call.parameters["id"]?.toInt() ?: -1
        val order = call.receive<OrderInfo>()
        if (id != -1) {
            transaction {
                OrdersInfoTable.update({ OrdersInfoTable.order_id eq id }) {
                    it[order_user_id] = order.order_user_id
                    it[order_name] = order.order_name
                    it[order_mail] = order.order_mail
                    it[order_phone] = order.order_phone
                    it[order_address] = order.order_address
                }
            }
            call.respond(HttpStatusCode.OK, "Order data has modified")
        } else call.respond(HttpStatusCode.NoContent)
    }
}

fun Route.searchOrder() {
    get("/order") {
        val orders: MutableList<OrderInfo> = ArrayList()
        transaction {
            val query = OrdersInfoTable.selectAll().toList()
            query.forEach {
                orders.add(
                    OrderInfo(
                        it[order_id],
                        it[order_user_id],
                        it[order_name],
                        it[order_address],
                        it[order_phone],
                        it[order_mail]
                    )
                )
            }
        }
        call.respond(HttpStatusCode.OK, orders)
    }

    get("/order/{id}") {
        val id: Int = call.parameters["id"]?.toInt() ?: -1
        val orders: MutableList<OrderInfo> = ArrayList()
        if (id != -1) {
            transaction {
                val query = OrdersInfoTable.select { OrdersInfoTable.order_id eq id }.toList()
                query.forEach {
                    orders.add(
                        OrderInfo(
                            id,
                            it[order_user_id],
                            it[order_name],
                            it[order_address],
                            it[order_phone],
                            it[order_mail]
                        )
                    )
                }
            }
            call.respond(HttpStatusCode.OK, orders)
        } else call.respond(HttpStatusCode.NoContent)
    }
}

fun Route.createOrder() {
    post("/order") {
        val order = call.receive<OrderInfo>()
        transaction {
            OrdersInfoTable.insert {
                it[order_user_id] = order.order_user_id
                it[order_name] = order.order_name
                it[order_address] = order.order_address
                it[order_phone] = order.order_phone
                it[order_mail] = order.order_mail
            }
        }
        call.respond(HttpStatusCode.Created, "Order inserted")
    }
}