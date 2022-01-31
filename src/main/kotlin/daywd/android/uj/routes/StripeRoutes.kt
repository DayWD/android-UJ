package daywd.android.uj.routes

import com.google.gson.Gson
import com.stripe.Stripe
import com.stripe.model.PaymentIntent
import com.stripe.param.PaymentIntentCreateParams
import daywd.android.uj.Keys
import daywd.android.uj.tables.ProductsTable
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.stripeSerialization() {
    routing {
        getStripe()
    }
}

fun Route.getStripe() {
    get("/stripe/{id}") {
        val id: Int = call.parameters["id"]?.toInt() ?: -1
        var price: Int = -1
        if (id != -1) {
            transaction {
                val query = ProductsTable.select { ProductsTable.product_id eq id }.toList()[0]
                price = query[ProductsTable.product_price]
            }

            Stripe.apiKey = Keys.stripeKey
            val params = PaymentIntentCreateParams.builder()
                .setAmount((price * 100).toLong())
                .setCurrency("pln")
                .build()

            val intent = PaymentIntent.create(params)
            call.respond(HttpStatusCode.OK, Gson().toJson(intent.clientSecret))
        } else call.respond(HttpStatusCode.NoContent)

    }
}
