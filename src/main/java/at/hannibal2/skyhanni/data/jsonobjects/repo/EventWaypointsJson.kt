package at.hannibal2.skyhanni.data.jsonobjects.repo

import at.hannibal2.skyhanni.utils.LorenzVec
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventWaypointsJson(
    val presents: Map<String, List<EventWaypointData>>,
    @SerialName("presents_entrances") val presentsEntrances: Map<String, List<EventWaypointData>>,
)

@Serializable
data class EventWaypointData(
    val name: String,
    val position: LorenzVec,
)
