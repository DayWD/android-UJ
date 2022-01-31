package daywd.android.uj

import daywd.android.uj.plugins.configureRouting
import daywd.android.uj.plugins.configureSerialization
import daywd.android.uj.tables.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
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
        SchemaUtils.create(LocalizationsTable)
    }

    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureSerialization()
    }.start(wait = true)
}
