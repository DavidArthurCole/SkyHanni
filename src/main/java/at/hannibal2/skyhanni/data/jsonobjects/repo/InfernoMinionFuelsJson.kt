package at.hannibal2.skyhanni.data.jsonobjects.repo

import at.hannibal2.skyhanni.utils.NeuInternalName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InfernoMinionFuelsJson(
    @SerialName("inferno_minion_fuels") val minionFuels: List<NeuInternalName>,
)
