package at.hannibal2.skyhanni.data.jsonobjects.repo

import kotlinx.serialization.Serializable

@Serializable
data class LividSolverJson(
    val livids: Map<String, LividData>,
)

@Serializable
data class LividData(
    val skin: String,
    val name: String,
)
