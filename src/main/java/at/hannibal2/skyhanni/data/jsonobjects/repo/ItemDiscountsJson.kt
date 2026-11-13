package at.hannibal2.skyhanni.data.jsonobjects.repo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ItemDiscountsJson(
    @SerialName("item_price_coin_only") val itemPriceCoinOnly: Map<String, Int>,
    @SerialName("items_to_discount_by_area") val itemsToDiscountByArea: ItemsToDiscountByArea,
    @SerialName("scaling_discounts") val scalingDiscounts: ScalingDiscounts,
)

@Serializable
data class ItemsToDiscountByArea(
    val Emissary: List<String>,
)

@Serializable
data class ScalingDiscounts(
    val Emissary: Map<String, Double>,
)
