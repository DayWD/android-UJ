package daywd.android.uj

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import daywd.android.uj.plugins.*
import daywd.android.uj.tables.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {

    Database.connect("jdbc:sqlite:shop.sqlite", "org.sqlite.JDBC")
//    TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE

    transaction {
        SchemaUtils.create(UsersTable)
        SchemaUtils.create(CartsTable)
        SchemaUtils.create(CategoriesTable)
        SchemaUtils.create(OrdersInfoTable)
        SchemaUtils.create(ProductsTable)
    }

    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureSerialization()
    }.start(wait = true)
}
