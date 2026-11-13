package at.hannibal2.skyhanni.data.jsonobjects.repo.neu

import at.hannibal2.skyhanni.utils.NeuInternalName
import kotlinx.serialization.Serializable

@Serializable
data class NeuRNGScore(
    val catacombs: Map<String, Map<NeuInternalName, Long>>,
    val slayer: Map<String, Map<NeuInternalName, Long>>,
)
