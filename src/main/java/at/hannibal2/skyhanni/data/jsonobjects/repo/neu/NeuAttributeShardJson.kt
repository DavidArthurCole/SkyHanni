package at.hannibal2.skyhanni.data.jsonobjects.repo.neu

import at.hannibal2.skyhanni.utils.LorenzRarity
import at.hannibal2.skyhanni.utils.NeuInternalName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NeuAttributeShardJson(
    @SerialName("attribute_levelling") val attributeLevelling: Map<LorenzRarity, List<Int>>,
    @SerialName("unconsumable_attributes") val unconsumableAttributes: List<String>,
    val attributes: List<NeuAttributeShardData>,
)

@Serializable
data class NeuAttributeShardData(
    val bazaarName: NeuInternalName,
    val displayName: String,
    val rarity: LorenzRarity,
    val internalName: NeuInternalName,
    val abilityName: String = "",
    val family: List<String> = emptyList(),
    val alignment: String,
    val shardId: String,
)
