package daywd.android.uj.plugins

import daywd.android.uj.routes.*
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }
    userSerialization()
    productSerialization()
    orderInfoSerialization()
    categorySerialization()
    cartSerialization()
    localizationSerialization()
    stripeSerialization()
}