package at.hannibal2.skyhanni.data.jsonobjects.other

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SkyblockItemsDataJson(
    val items: List<SkyblockItemData>,
)

@Serializable
data class SkyblockItemData(
    val id: String?,
    @SerialName("npc_sell_price") val npcPrice: Double?,
    @SerialName("motes_sell_price") val motesPrice: Double?,
    @SerialName("stats") val stats: Map<String, Int>?,
)
