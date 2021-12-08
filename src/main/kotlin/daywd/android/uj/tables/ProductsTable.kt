package daywd.android.uj.tables

import org.jetbrains.exposed.sql.Table

object ProductsTable : Table("products") {
    val product_id = integer("product_id").autoIncrement()
    override val primaryKey = PrimaryKey(product_id)
    val product_price = integer("product_price")
    val product_name = varchar("product_name", 50)
    val product_category_id = integer("product_category_id").references(CategoriesTable.category_id)
    val description = varchar("description", 1000)
}