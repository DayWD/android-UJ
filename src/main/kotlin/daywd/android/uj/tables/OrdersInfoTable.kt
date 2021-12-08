package daywd.android.uj.tables

import org.jetbrains.exposed.sql.Table

object OrdersInfoTable : Table("orders_info") {
    val order_id = integer("order_id").autoIncrement()
    override val primaryKey = PrimaryKey(order_id)
    val order_user_id = integer("order_user_id").references(UsersTable.user_id)
    val order_name = varchar("order_name",50)
    val order_address = varchar("order_address",50)
    val order_phone = integer("order_phone")
    val order_mail = varchar("order_mail",50)
}