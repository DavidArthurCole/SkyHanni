package at.hannibal2.skyhanni.data.jsonobjects.repo

import at.hannibal2.skyhanni.utils.LorenzVec
import kotlinx.serialization.Serializable

@Serializable
data class MetalDetectorChestsJson(
    val locations: List<LorenzVec>,
)
