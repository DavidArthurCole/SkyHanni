package at.hannibal2.skyhanni.data.jsonobjects.repo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IslandTypeJson(
    val islands: Map<String, IslandJson>,
    @SerialName("max_players") val maxPlayers: Int,
    @SerialName("max_players_mega") val maxPlayersMega: Int,
)

@Serializable
data class IslandJson(
    val name: String,
    @SerialName("api_name") val apiName: String? = null,
    @SerialName("max_players") val maxPlayers: Int? = null,
    val bounds: IslandBounds? = null,
)

@Serializable
data class IslandBounds(
    @SerialName("max_x") val maxX: Int,
    @SerialName("min_x") val minX: Int,
    @SerialName("max_z") val maxZ: Int,
    @SerialName("min_z") val minZ: Int,
)
