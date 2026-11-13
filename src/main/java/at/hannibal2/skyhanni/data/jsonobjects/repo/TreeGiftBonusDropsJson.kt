package at.hannibal2.skyhanni.data.jsonobjects.repo

import at.hannibal2.skyhanni.utils.NeuInternalName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TreeGiftBonusDropsJson(
    @SerialName("uncommon_drops") val uncommonDrops: List<NeuInternalName>,
    @SerialName("enchanted_books") val enchantedBooks: List<NeuInternalName>,
    val boosters: List<NeuInternalName>,
    val shards: List<NeuInternalName>,
    val mobs: List<String>,
    val runes: List<NeuInternalName>,
    @SerialName("misc_drops") val miscDrops: List<NeuInternalName>,
)
