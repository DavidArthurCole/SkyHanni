package at.hannibal2.skyhanni.data.jsonobjects.repo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HideNotClickableItemsJson(
    @SerialName("hide_npc_sell") val hideNpcSell: MultiFilterJson,
    @SerialName("hide_in_storage") val hideInStorage: MultiFilterJson,
    @SerialName("hide_player_trade") val hidePlayerTrade: MultiFilterJson,
    @SerialName("not_auctionable") val notAuctionable: MultiFilterJson,
    val salvage: SalvageFilter,
)

@Serializable
data class SalvageFilter(
    val armor: List<String>,
    val items: List<String>,
)
