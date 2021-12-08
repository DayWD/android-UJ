package daywd.android.uj.plugins

import daywd.android.uj.routes.*
import io.ktor.gson.*
import io.ktor.features.*
import io.ktor.application.*

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        gson {
//            setPrettyPrinting()
        }
    }
    userSerialization();
    productSerialization()
    orderInfoSerialization()
    categorySerialization()
    cartSerialization()
}