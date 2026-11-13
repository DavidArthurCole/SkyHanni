package at.hannibal2.skyhanni.data.jsonobjects.repo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FameRankJson(
    @SerialName("fame_rank") val fameRank: Map<String, FameRank>,
)

@Serializable
data class FameRank(
    val name: String,
    @SerialName("fame_required") val fameRequired: Int,
    @SerialName("bits_multiplier") val bitsMultiplier: Double,
    val votes: Int,
)
