package at.hannibal2.skyhanni.data.jsonobjects.repo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnitaUpgradeCostsJson(
    @SerialName("level_price") val levelPrice: Map<Int, AnitaUpgradePrice>,
)

@Serializable
data class AnitaUpgradePrice(
    @SerialName("gold_medals") val goldMedals: Int,
    @SerialName("jacob_tickets") val jacobTickets: Int,
)
