package daywd.android.uj.routes

import daywd.android.uj.models.Localization
import daywd.android.uj.tables.LocalizationsTable
import daywd.android.uj.tables.LocalizationsTable.description
import daywd.android.uj.tables.LocalizationsTable.latitude
import daywd.android.uj.tables.LocalizationsTable.localization_id
import daywd.android.uj.tables.LocalizationsTable.longitude
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.localizationSerialization() {
    routing {
        createLocalization()
        searchLocalization()
        editLocalization()
        deleteLocalization()
    }
}

fun Route.deleteLocalization() {
    delete("/localization/{id}") {
        val id: Int = call.parameters["id"]?.toInt() ?: -1
        if (id != -1) {
            transaction {
                LocalizationsTable.deleteWhere { localization_id eq id }
            }
            call.respond(HttpStatusCode.OK, "Localization Deleted")
        } else call.respond(HttpStatusCode.NoContent)
    }
}

fun Route.editLocalization() {
    put("/localization/{id}") {
        val id: Int = call.parameters["id"]?.toInt() ?: -1
        val localization = call.receive<Localization>()
        if (id != -1) {
            transaction {
                LocalizationsTable.update({ localization_id eq id }) {
                    it[latitude] = localization.latitude
                    it[longitude] = localization.longitude
                    it[description] = localization.description
                }
            }
            call.respond(HttpStatusCode.OK, "Localization data has modified")
        } else call.respond(HttpStatusCode.NoContent)
    }
}

fun Route.searchLocalization() {
    get("/localization") {
        val localizations: MutableList<Localization> = ArrayList()
        transaction {
            val query = LocalizationsTable.selectAll().toList()
            query.forEach {
                localizations.add(Localization(it[localization_id], it[latitude], it[longitude], it[description]))
            }
        }
        call.respond(HttpStatusCode.OK, localizations)
    }

    get("/localization/{id}") {
        val id: Int = call.parameters["id"]?.toInt() ?: -1
        var localization = Localization(id)
        if (id != -1) {
            transaction {
                val query = LocalizationsTable.select { localization_id eq id }.toList()[0]
                localization = Localization(id, query[latitude], query[longitude], query[description])
            }
            call.respond(HttpStatusCode.OK, localization)
        } else call.respond(HttpStatusCode.NoContent)
    }
}

fun Route.createLocalization() {
    post("/localization") {
        val localization = call.receive<Localization>()
        transaction {
            LocalizationsTable.insert {
                it[latitude] = localization.latitude
                it[longitude] = localization.longitude
                it[description] = localization.description
            }
        }
        call.respond(HttpStatusCode.Created, "Localization inserted")
    }
}