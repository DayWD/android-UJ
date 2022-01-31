package daywd.android.uj.tables

import org.jetbrains.exposed.sql.Table

object CategoriesTable : Table("categories") {
    val category_id = integer("category_id").autoIncrement() // unique
    override val primaryKey = PrimaryKey(category_id)
    val category_name = varchar("category_name", 50) // unique
}