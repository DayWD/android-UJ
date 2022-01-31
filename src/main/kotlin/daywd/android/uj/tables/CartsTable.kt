package daywd.android.uj.tables

import org.jetbrains.exposed.sql.Table

object CartsTable : Table("carts") {
    val cart_id = integer("cart_id").autoIncrement() // unique
    override val primaryKey = PrimaryKey(cart_id)
    val cart_user_id = integer("cart_user_id").references(UsersTable.user_id)
    val cart_product_id = integer("cart_product_id").references(ProductsTable.product_id)
    val cart_product_quantity = integer("cart_product_quantity")
}
