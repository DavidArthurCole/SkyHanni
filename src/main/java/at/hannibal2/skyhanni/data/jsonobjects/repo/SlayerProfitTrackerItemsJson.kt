package at.hannibal2.skyhanni.data.jsonobjects.repo

import at.hannibal2.skyhanni.utils.NeuInternalName
import kotlinx.serialization.Serializable

@Serializable
data class SlayerProfitTrackerItemsJson(
    val slayers: Map<String, List<NeuInternalName>>,
)
