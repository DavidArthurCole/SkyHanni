package at.hannibal2.skyhanni.features.mining.eventtracker

import at.hannibal2.skyhanni.data.IslandType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MiningEventDataSend(
    @SerialName("server_type") val serverType: IslandType,
    @SerialName("server_id") val serverId: String,
    val event: MiningEventType,
    @SerialName("time_left") val timeRemaining: Long,
    @SerialName("reporter_uuid") val uuid: String,
)

@Serializable
data class MiningEventDataReceive(
    val success: Boolean,
    val data: MiningEventData?,
    val cause: String?,
)

@Serializable
data class MiningEventData(
    @SerialName("event_datas") val eventData: Map<IslandType, Map<MiningEventType, EventData>>,
    @SerialName("running_events") val runningEvents: Map<IslandType, List<RunningEventType>>,
    @SerialName("total_lobbys") val totalLobbies: Map<IslandType, Int>,
    @SerialName("update_in") val updateIn: Long,
    @SerialName("curr_time") val currentTime: Long,
)

@Serializable
data class EventData(
    @SerialName("starts_at_min") val startMin: Long,
    @SerialName("starts_at_max") val startMax: Long,
    @SerialName("ends_at_min") val endMin: Long,
    @SerialName("ends_at_max") val endMax: Long,
    @SerialName("lobby_count") val lobbyCount: Int,
)

@Serializable
data class RunningEventType(
    val event: MiningEventType,
    @SerialName("ends_at") val endsAt: Long,
    @SerialName("lobby_count") val lobbyCount: Int,
    @SerialName("is_double") val isDoubleEvent: Boolean,
)
