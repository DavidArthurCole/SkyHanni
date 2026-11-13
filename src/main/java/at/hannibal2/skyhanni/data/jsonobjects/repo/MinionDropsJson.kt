package at.hannibal2.skyhanni.data.jsonobjects.repo

import at.hannibal2.skyhanni.utils.NeuInternalName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MinionDropsJson(
    @SerialName("fuel_drops") val fuelDrops: Map<NeuInternalName, Set<NeuInternalName>>,
    val minions: Map<String, Set<NeuInternalName>>,
)
