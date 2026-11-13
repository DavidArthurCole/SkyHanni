package at.hannibal2.skyhanni.data.jsonobjects.repo

import at.hannibal2.skyhanni.utils.LorenzVec
import kotlinx.serialization.Serializable

@Serializable
data class RiftWoodenButtonsJson(
    val houses: Map<String, List<ButtonSpots>>,
)

@Serializable
data class ButtonSpots(
    val position: LorenzVec,
    val buttons: List<LorenzVec>,
)
