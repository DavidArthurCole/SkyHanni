package at.hannibal2.skyhanni.data.jsonobjects.repo

import at.hannibal2.skyhanni.utils.NeuInternalName
import kotlinx.serialization.Serializable

@Serializable
data class FishingProfitItemsJson(
    val categories: Map<String, List<NeuInternalName>>,
)
