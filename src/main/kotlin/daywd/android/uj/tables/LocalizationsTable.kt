package daywd.android.uj.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object LocalizationsTable : Table("localizations") {
    val localization_id: Column<Int> = integer("localization_id").autoIncrement()
    override val primaryKey = PrimaryKey(localization_id)
    val latitude = double("latitude")
    val longitude = double("longitude")
    val description = varchar("description", 50)
}