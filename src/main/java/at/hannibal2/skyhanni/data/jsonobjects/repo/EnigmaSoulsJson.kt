package at.hannibal2.skyhanni.data.jsonobjects.repo

import at.hannibal2.skyhanni.utils.LorenzVec
import kotlinx.serialization.Serializable

@Serializable
data class EnigmaSoulsJson(
    val areas: Map<String, List<EnigmaPosition>>,
)

@Serializable
data class EnigmaPosition(
    val name: String,
    val position: LorenzVec,
)
