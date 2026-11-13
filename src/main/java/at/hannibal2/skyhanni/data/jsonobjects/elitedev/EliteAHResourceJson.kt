package at.hannibal2.skyhanni.data.jsonobjects.elitedev

import at.hannibal2.skyhanni.utils.LorenzRarity
import at.hannibal2.skyhanni.utils.NeuInternalName
import kotlinx.serialization.Serializable

@Serializable
data class EliteAuctionsResponse(
    val items: Map<NeuInternalName, List<EliteAuctionItem>>,
)

@Serializable
data class EliteLowestSet(
    val lowest: Long,
    val lowestVolume: Int,
)

@Serializable
data class EliteVariedBy(
    val rarity: LorenzRarity,
    val pet: String? = null,
    val petLevel: EliteVariedByPetLevel? = null,
    val extra: Map<String, String>? = null,
)

@Serializable
data class EliteVariedByPetLevel(
    val key: String,
    val min: Int,
    val max: Int,
)

@Serializable
data class EliteAuctionItem(
    private val skyblockId: NeuInternalName,
    val variantKey: String,
    val variedBy: EliteVariedBy,
    private val lowest: Long,
    private val lowestVolume: Int,
    private val lowest3Day: Long,
    private val lowest3DayVolume: Int,
    private val lowest7Day: Long,
    private val lowest7DayVolume: Int,
) {
    val internalName: NeuInternalName get() = skyblockId
    val lowestSet: EliteLowestSet get() = EliteLowestSet(lowest, lowestVolume)
    val lowest3DaySet: EliteLowestSet get() = EliteLowestSet(lowest3Day, lowest3DayVolume)
    val lowest7DaySet: EliteLowestSet get() = EliteLowestSet(lowest7Day, lowest7DayVolume)
}
