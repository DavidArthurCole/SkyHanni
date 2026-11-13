package at.hannibal2.skyhanni.data.jsonobjects.repo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MiningJson(
    @SerialName("block_strengths") val blockStrengths: Map<String, Int>,
)
