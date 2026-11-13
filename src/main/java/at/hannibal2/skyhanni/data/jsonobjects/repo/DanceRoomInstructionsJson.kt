package at.hannibal2.skyhanni.data.jsonobjects.repo

import kotlinx.serialization.Serializable

@Serializable
data class DanceRoomInstructionsJson(
    val instructions: List<String>,
)
