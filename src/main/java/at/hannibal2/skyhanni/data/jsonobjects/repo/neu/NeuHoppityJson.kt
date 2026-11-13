package at.hannibal2.skyhanni.data.jsonobjects.repo.neu

import kotlinx.serialization.Serializable

@Serializable
data class NeuHoppityJson(
    val hoppity: HoppityInfo,
)

@Serializable
data class HoppityInfo(
    val rarities: Map<String, HoppityRarityInfo>,
    val special: Map<String, MythicRabbitInfo>,
)

@Serializable
data class HoppityRarityInfo(
    val rabbits: List<String>,
    val chocolate: Int,
    val multiplier: Double,
)

@Serializable
data class MythicRabbitInfo(
    val chocolate: Int,
    val multiplier: Double,
)
