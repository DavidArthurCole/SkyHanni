package at.hannibal2.skyhanni.data.jsonobjects.repo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MinionXPJson(
    @SerialName("minion_xp") val minionXP: Map<String, Map<String, Double>>,
)
