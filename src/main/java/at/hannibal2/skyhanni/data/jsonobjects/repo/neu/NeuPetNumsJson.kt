package at.hannibal2.skyhanni.data.jsonobjects.repo.neu

import at.hannibal2.skyhanni.data.model.SkyblockStat
import at.hannibal2.skyhanni.utils.LorenzRarity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Pet "proper internal name" -> ...
typealias NeuPetNumsJson = Map<String, PetSpecificNums>
// Rarity -> ...
private typealias PetSpecificNums = Map<LorenzRarity, NEURaritySpecificPetNums>

@Serializable
data class NEURaritySpecificPetNums(
    @SerialName("1") val min: NeuPetNums,
    @SerialName("100") val max: NeuPetNums,
    @SerialName("stats_levelling_curve") private val levelCurveString: String? = null,
) {
    private val curveSplits by lazy { levelCurveString?.split(":")?.map { it.toInt() }.orEmpty() }
    val minStatsLevel: Int? get() = curveSplits.getOrNull(0)
    val maxStatsLevel: Int? get() = curveSplits.getOrNull(1)
    val statLevellingType: Int? get() = curveSplits.getOrNull(2)
}

@Serializable
data class NeuPetNums(
    val otherNums: List<Double>,
    val statNums: Map<SkyblockStat, Double>,
)
