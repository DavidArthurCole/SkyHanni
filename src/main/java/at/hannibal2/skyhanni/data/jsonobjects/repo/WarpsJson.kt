package at.hannibal2.skyhanni.data.jsonobjects.repo

import at.hannibal2.skyhanni.data.IslandType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class WarpsJson(
    @SerialName("warp_commands") @JsonNames("warpCommands") val warpCommands: List<String>,
    @SerialName("warp_location") @JsonNames("warpLocation") val warpLocation: Map<String, WarpLocationData>,
)

@Serializable
data class WarpLocationData(
    @SerialName("display_name") val displayName: String,
    val island: IslandType,
    val x: Double,
    val y: Double,
    val z: Double,
    @SerialName("extra_diana_warp_blocks") val extraDianaWarpBlocks: Int = 0,
)
