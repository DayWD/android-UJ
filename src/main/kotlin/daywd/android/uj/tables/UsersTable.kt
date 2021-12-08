package daywd.android.uj.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object UsersTable : Table("users") {
    val user_id: Column<Int> = integer("user_id").autoIncrement()
    override val primaryKey = PrimaryKey(user_id)
    val user_login = varchar("user_login", 50) //unique
    val user_email = varchar("user_email", 50) //unique
    val user_password = varchar("user_password", 50) //unique
}