package at.hannibal2.skyhanni.data.jsonobjects.repo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BazaarJson(
    @SerialName("daily_limit") val dailyLimit: Double,
    @SerialName("cap_orders_at_integer_limit") val capOrdersAtIntLimit: Boolean,
)
