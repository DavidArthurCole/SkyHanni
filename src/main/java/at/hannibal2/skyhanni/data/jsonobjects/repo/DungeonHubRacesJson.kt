package at.hannibal2.skyhanni.data.jsonobjects.repo

import kotlinx.serialization.Serializable

@Serializable
data class DungeonHubRacesJson(
    val data: Map<String, Map<String, ParkourJson>>,
)
