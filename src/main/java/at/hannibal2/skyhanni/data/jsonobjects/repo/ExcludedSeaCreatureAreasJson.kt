package at.hannibal2.skyhanni.data.jsonobjects.repo

import at.hannibal2.skyhanni.data.IslandType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class ExcludedSeaCreatureAreasJson(
    @SerialName("excluded_islands") @JsonNames("excludedIslands") val excludedIslands: Set<IslandType>? = null,
    @SerialName("excluded_graph_areas") @JsonNames("excludedGraphAreas") val excludedGraphAreas: Set<String>? = null,
)
