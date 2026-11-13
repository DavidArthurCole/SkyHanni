package at.hannibal2.skyhanni.data.jsonobjects.repo

import at.hannibal2.skyhanni.utils.NeuInternalName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GhostDropsJson(
    @SerialName("ghost_drops") val ghostDrops: Set<NeuInternalName>,
    @SerialName("sacks_drops") val sacksDrops: Set<NeuInternalName>,
)
