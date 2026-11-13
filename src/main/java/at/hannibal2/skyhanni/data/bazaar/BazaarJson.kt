package at.hannibal2.skyhanni.data.bazaar

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class BazaarApiResponseJson(
    val success: Boolean,
    val cause: String? = null,
    val lastUpdated: Long,
    val products: Map<String, BazaarProduct>,
)

@Serializable
data class BazaarProduct(
    @SerialName("product_id") val productId: String,
    @SerialName("quick_status") val quickStatus: BazaarQuickStatus,
    @SerialName("sell_summary") val sellSummary: List<BazaarSummary>,
    @SerialName("buy_summary") val buySummary: List<BazaarSummary>,
)

@Serializable
class BazaarQuickStatus(
    val productId: String,
    val sellPrice: Double,
    val sellVolume: Long,
    val sellMovingWeek: Long,
    val sellOrders: Long,
    val buyPrice: Double,
    val buyVolume: Long,
    val buyMovingWeek: Long,
    val buyOrders: Long,
)

@Serializable
data class BazaarSummary(
    val amount: Long,
    val pricePerUnit: Double,
    val orders: Long,
)
