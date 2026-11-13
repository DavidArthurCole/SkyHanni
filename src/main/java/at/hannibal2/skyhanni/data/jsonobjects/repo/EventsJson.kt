package at.hannibal2.skyhanni.data.jsonobjects.repo

import at.hannibal2.skyhanni.utils.SimpleTimeMark
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventsJson(
    @SerialName("great_spook") val greatSpook: Map<String, SimpleTimeMark>,
)
