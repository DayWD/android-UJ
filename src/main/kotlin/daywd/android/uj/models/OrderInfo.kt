package daywd.android.uj.models

data class OrderInfo (
    val order_id: Int = -1,
    val order_user_id: Int = -1,
    val order_name: String = "",
    val order_address: String = "",
    val order_phone: Int = -1,
    val order_mail: String = ""
    )
// Każdy User może mieć wiele profili Orders.
// order_user_Id = UserID