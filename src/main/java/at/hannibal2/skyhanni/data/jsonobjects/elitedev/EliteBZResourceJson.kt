package at.hannibal2.skyhanni.data.jsonobjects.elitedev

import at.hannibal2.skyhanni.utils.NeuInternalName
import kotlinx.serialization.Serializable

@Serializable
data class EliteBazaarResponse(
    val products: Map<NeuInternalName, EliteBazaarProduct>,
)

@Serializable
data class EliteBazaarProduct(
    val name: String? = null,
    val npc: Double? = null,
    val sell: Double,
    val buy: Double,
    val sellOrder: Double,
    val buyOrder: Double,
    val averageSell: Double,
    val averageBuy: Double,
    val averageSellOrder: Double,
    val averageBuyOrder: Double,
)
