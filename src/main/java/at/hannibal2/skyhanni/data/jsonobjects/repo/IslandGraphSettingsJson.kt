package at.hannibal2.skyhanni.data.jsonobjects.repo

import at.hannibal2.skyhanni.data.IslandType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IslandGraphSettingsJson(
    @SerialName("ignored_island_types") val ignoredIslandTypes: Set<IslandType>,
)
