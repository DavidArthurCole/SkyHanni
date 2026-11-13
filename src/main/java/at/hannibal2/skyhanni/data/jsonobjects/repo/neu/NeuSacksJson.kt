package at.hannibal2.skyhanni.data.jsonobjects.repo.neu

import at.hannibal2.skyhanni.utils.NeuInternalName
import kotlinx.serialization.Serializable

@Serializable
data class NeuSacksJson(
    val sacks: Map<String, SackInfo>,
)

@Serializable
data class SackInfo(
    val item: NeuInternalName,
    val contents: List<NeuInternalName>,
)
