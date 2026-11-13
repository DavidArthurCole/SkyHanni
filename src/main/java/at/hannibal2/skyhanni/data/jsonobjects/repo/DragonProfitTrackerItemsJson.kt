package at.hannibal2.skyhanni.data.jsonobjects.repo

import at.hannibal2.skyhanni.utils.NeuInternalName
import kotlinx.serialization.Serializable

@Serializable
data class DragonProfitTrackerItemsJson(
    val items: Map<NeuInternalName, DragonProfitTrackerItemDataJson>,
)

@Serializable
data class DragonProfitTrackerItemDataJson(
    val weight: Int,
)
