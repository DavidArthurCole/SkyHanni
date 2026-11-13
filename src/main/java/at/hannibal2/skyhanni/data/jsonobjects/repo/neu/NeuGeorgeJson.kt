package at.hannibal2.skyhanni.data.jsonobjects.repo.neu

import at.hannibal2.skyhanni.utils.NeuInternalName
import kotlinx.serialization.Serializable

@Serializable
data class NeuGeorgeJson(
    val prices: Map<NeuInternalName, Double>?,
)
